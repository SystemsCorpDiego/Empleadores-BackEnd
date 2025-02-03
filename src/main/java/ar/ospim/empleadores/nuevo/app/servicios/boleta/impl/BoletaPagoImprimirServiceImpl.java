package ar.ospim.empleadores.nuevo.app.servicios.boleta.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.BancoMovimientoBO;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.FormaPagoBO;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BarCode;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoImprimirService;
import ar.ospim.empleadores.nuevo.app.servicios.formapago.FormaPagoService;
import ar.ospim.empleadores.nuevo.infra.out.store.AporteStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.BoletaPagoStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.DDJJStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoletaPagoImprimirServiceImpl implements BoletaPagoImprimirService {
	JasperReport boletaJasper;
	JasperReport boletaDdjjJasper;
	
	@Autowired
	private DataSource dataSource;
	 
	private final MessageSource messageSource;
	private final BoletaPagoStorage boletaPagoStorage;
	private final AporteStorage aporteStorage;
	private final DDJJStorage ddjjStorage;
	private final FormaPagoService formaPagoService;
	private final DateTimeProvider dateTimeProvider; 
	
	@PostConstruct
	private void init() {
		log.debug("init(): ARRANQUE...");
		//log.debug("*** datasourceUrl: " + datasourceUrl);
		
		try {			
			InputStream fileJasper = getClass().getClassLoader().getResourceAsStream("reportes/boletaPagoGenerico.jrxml");
			boletaJasper = JasperCompileManager.compileReport(fileJasper);
			
			fileJasper = getClass().getClassLoader().getResourceAsStream("reportes/boletaPagoDDJJ.jrxml");
			boletaDdjjJasper = JasperCompileManager.compileReport(fileJasper);
			
			//con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sigecoDB", "postgres","Local123");
			//con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","tecnicos","Valkyria01"); //SystemsCorp
			
		}  catch(Exception e) {
			log.error("init() - ERROR : " +  e.getMessage() +" - "+ e.getCause() +" - "+ e.getStackTrace() );
		}
	 }
	
	public byte[] run(Integer id)  throws JRException, SQLException {		
		Optional<BoletaPagoBO> reg = boletaPagoStorage.findById(id);
		if ( reg.isEmpty() ) {		
			String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), String.format(errorMsg, id)	);
		}
		if ( reg.get().getBaja() != null ) {		
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.REGISTRO_BAJA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.REGISTRO_BAJA.name(), String.format(errorMsg, id)	);
		}
		
		return run(reg.get());
	}
	
	
	public byte[] run(BoletaPagoBO boleta)  throws JRException, SQLException {
		byte[] pdfBytes = null;
		HashMap<String, Object> params = null;
		
		BancoMovimientoBO movBancario = aporteStorage.findMovimientoByAporte(boleta.getAporte().getCodigo());
		FormaPagoBO formaPago = formaPagoService.get(boleta.getFormaDePago());
		LocalDate hoy = LocalDate.now();

		try {
	    	params = new HashMap<String, Object>();
	    	
	    	params.put("boletaId", boleta.getId() );
	    	
	    	params.put("logo", "reportes/UOMA_AZUL.png" );
	    	params.put("codigoBarra", BarCode.generarCodigoBarras(getCodigoBarras(boleta, movBancario)) );
	    	
	    	if ( !formaPagoService.generaVEP(boleta.getFormaDePago()) ) {
	    		params.put("codigoBarraNro", getCodigoBarras(boleta, movBancario) );
	    		params.put("bancoDescrip", movBancario.getBancoDescripcion() );
	    	} else {
	    		params.put("bancoDescrip", "BEP " +  formaPago.getDescripcion() );
	    		params.put("codigoBarraNro", "Boleta de Pago Electrónica. Canal: " + formaPago.getDescripcion() );
	    	}
	    	
	    	 
	    	params.put("cuentaNro", movBancario.getConvenioCuenta() ); 
	    	params.put("sucursalCodigo", movBancario.getSucursalCodigo() ); 
	    	params.put("sucursalDescrip", movBancario.getSucursalDescripcion() ); 	    	
	    	params.put("boletaNro", StringUtils.leftPad(String.valueOf(boleta.getSecuencia()), 6, "0") ); 
	    	params.put("entidadDescrip", boleta.getAporte().getEntidad() ); 
	    	params.put("empleadorCUIT", boleta.getEmpresa().getCuit() ); 
	    	params.put("empleadorRazonSocial", boleta.getEmpresa().getRazonSocial() );
	    	params.put("fechaImpresion", dateTimeProvider.getDateToString(hoy) ); 
	    	params.put("fechaIntencionPago",  dateTimeProvider.getDateToString(boleta.getIntencionDePago()));
	    	if ( boleta.getVencimiento() != null ) 
	    		params.put("fechaVencimiento",  dateTimeProvider.getDateToString(boleta.getVencimiento()));
	    	
	    	params.put("motivoPagoDescrip", boleta.getDescripcion() );
	    	params.put("importe", boleta.getImporte() ); 
	    	params.put("formaPago", boleta.getFormaDePago() ); 
	    	params.put("aporteDescrip", boleta.getAporte().getDescripcion() ); 
	    	
	    	//params.put("P_logoPath1", "reportes\\UOMA-trad.jpg");
	    	//params.put("P_logoPath2", "reportes\\\\UOMA-molino.jpg");
	    	JasperPrint print = null;
	    	Connection con = dataSource.getConnection();
	    	if ( boleta.getDdjjId() == null ) {
	    		print = JasperFillManager.fillReport(boletaJasper, params, con);
	    	} else {
	    		print = JasperFillManager.fillReport(boletaDdjjJasper, params, con);
	    	}
			pdfBytes = JasperExportManager.exportReportToPdf(print);
			con.close();
		} catch(Exception e) {
			log.error( e.toString() );
			throw e;
		}
		
		if ( boleta.getImpresion() == null  ) {
			boleta.setImpresion( LocalDate.now() );
			boletaPagoStorage.registrarImpresion(boleta.getId());			
		}
	    log.debug( "FIN " );
		return pdfBytes;
	}
	 
	
	private String getCodigoBarras(BoletaPagoBO boleta, BancoMovimientoBO movBancario) {
		StringBuilder sb = new StringBuilder();
		
		//sb.append(tipoBoleta.getCodigoConvenio());
		sb.append(movBancario.getConvenioCodigo());		
		sb.append(boleta.getEmpresa().getCuit());
		
		//Seteo del Periodo:
		//sb.append(formatter.format(declaracionJurada.getPeriodo()));
		if ( boleta.getDdjjId() == null) {
			sb.append("000000"); //TODO: Boleta Blanca NO tiene Periodo, ver que hacemos
			sb.append("00"); //Secuencia ddjj			
		} else {
			Optional<DDJJBO> ddjj = ddjjStorage.findById(boleta.getDdjjId());
			sb.append(ddjj.get().getPeriodo().format( DateTimeFormatter.ofPattern("MMyyyy") ));
			sb.append(StringUtils.leftPad(
					String.valueOf(ddjj.get().getSecuencia()), 2,
					"0"));
		}
		sb.append(StringUtils.leftPad(String.valueOf(boleta.getSecuencia()), 4, "0"));
		
		sb.append("00");
		//sb.append(entidadBoleta.getTipoNro());
		sb.append(movBancario.getTipo());
		
		return sb.toString();
	}
	
}
