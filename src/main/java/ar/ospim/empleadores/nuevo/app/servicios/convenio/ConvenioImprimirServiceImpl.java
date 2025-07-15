package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.io.InputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCuotaConsultaDto;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.ConvenioEstadoEnum;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.EntidadEnum;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuota;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConvenioImprimirServiceImpl implements ConvenioImprimirService {

	private JasperReport convenioJasper;	
	
	@Autowired
	private final ConvenioService service;
	
	@Autowired
	private DateTimeProvider dtProvider;
	
	
	private String dfStr = "#,##0.00";
	
	private DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();	
	private DecimalFormat df = null; 
	
	@PostConstruct
	private void init() {
		log.debug("init(): ARRANQUE...");
		try {
			
			InputStream fleJasper = getClass().getClassLoader().getResourceAsStream("reportes/convenio.jrxml");
			convenioJasper = JasperCompileManager.compileReport(fleJasper);
 
			decimalFormatSymbols.setDecimalSeparator(',');
			decimalFormatSymbols.setGroupingSeparator('.');
			df =  new DecimalFormat(dfStr, decimalFormatSymbols);
			
			//con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sigecoDB", "postgres","Local123");
			//con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","tecnicos","Valkyria01"); //SystemsCorp
				
		}  catch(Exception e) {
			log.error("init() - ERROR : " +  e.getMessage() +" - "+ e.getCause() +" - "+ e.getStackTrace() );
		}
	 }
	
	@Override
	public byte[] run(Integer id)  throws JRException, SQLException {
		byte[] pdfBytes = null;
		
		HashMap<String, Object> params =  getParameters(id);
		Vector vecDetalle = getDetalle(id);
		
		JRDataSource dataSourceDetalle = new JRMapCollectionDataSource(vecDetalle); //Son Fields en el jasper
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(convenioJasper, params, dataSourceDetalle);
		
		pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
		
		log.debug( "FIN " );
		return pdfBytes;
	}
	
	private HashMap<String, Object> getParameters(Integer convenioId) {		
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		Convenio convenio = service.get(1, convenioId);
				 
		
		EntidadEnum entidad = EntidadEnum.map(  convenio.getEntidad() );
		params.put("tituloEntidad", entidad.getDescripcion()); 
		
		params.put("P_logoPath", "reportes/UOMA-trad.jpg");	    	
		
		
		
		params.put("empresaRazonSocial", convenio.getEmpresa().getRazonSocial());
		params.put("empresaCuit", convenio.getEmpresa().getCuit());
		
		ConvenioEstadoEnum estado = ConvenioEstadoEnum.map(  convenio.getEstado() );
		params.put("convenioEstado", estado.getDescripcion());
	
    	if ( convenio.getIntencionDePago() != null)  { 
			params.put("fechaIntencionPago", dtProvider.getDateToString( convenio.getIntencionDePago()) );
    	} else {
    		params.put("fechaIntencionPago", " " );
    	}
	
    	if (convenio.getImporteDeuda() != null) {
    		params.put("convenioImporteDeuda", df.format(convenio.getImporteDeuda()) );
    	} else {
    		params.put("convenioImporteDeuda", "0,00");
    	}
    	if ( convenio.getImporteSaldoFavor() != null) {
    		params.put("convenioImporteSaldoFavor",  df.format(convenio.getImporteSaldoFavor()) );
    	} else {
    		params.put("convenioImporteSaldoFavor",  "0,00" );
    	}
    	
    	if ( convenio.getImporteIntereses() != null) {
    		params.put("convenioImporteTotalIntereses",  df.format(convenio.getImporteIntereses()) );
    	} else {
    		params.put("convenioImporteTotalIntereses",   "0,00"  );
    	}

    	if ( convenio.getCuotasCanti() != null) {
    		params.put("convenioCuotasCantidad", convenio.getCuotasCanti().toString());
    	} else {
    		params.put("convenioCuotasCantidad", " ");
    	}
		
		if (  convenio.getImporteSaldoFavor() != null ) {
			
		}
		
	// "convenioImporteFinanciar" 
	// "convenioImporteTotal"

		return params;
	}
	
	private Vector getDetalle(Integer convenioId) {		
		
		List<ConvenioCuotaConsultaDto>  lst = service.getCuotas(1,  convenioId);
		
		Vector vecDetalle	= null;
		
		if ( lst != null && lst.size() > 0) {
			vecDetalle	= new Vector(); //vector que va al jasper
			HashMap registro = null; 
			
			for(ConvenioCuotaConsultaDto cuota :lst) {
				registro = new HashMap(); 
				if ( cuota.getNumero() != null) {
					registro.put("cuotaNro", cuota.getNumero().toString());
				} else {
					registro.put("cuotaNro", " ");
				}
				if ( cuota.getImporte() != null ) {
					registro.put("cuotaImporte", df.format(cuota.getImporte()) );
				} else {
					registro.put("cuotaImporte", "0,00" );
				}
		    	if ( cuota.getVencimiento() != null) {
		    		registro.put("cuotaVto", dtProvider.getDateToString( cuota.getVencimiento()) );
		    	} else {
		    		registro.put("cuotaVto", " ");
		    	}
				vecDetalle.add(registro);
			}
		}
		
		return vecDetalle;
		/*
		
		
		
		registro = new HashMap(); //cada registro es un HashMap
		registro.put("domicilio", domicilio);
		
		vecDetalle.add(registro);
		JRDataSource dataSourceDetalle = new JRMapCollectionDataSource(vecDetalle); //Son Fields en el jasper
		exporter.addParamQR(params);

		// CertificadosEnComisionAction.generarCertificado()
		
		*/
	}
	
}
