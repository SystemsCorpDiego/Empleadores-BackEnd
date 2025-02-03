package ar.ospim.empleadores.nuevo.app.servicios.boleta.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoDetalleImprimirService;
import ar.ospim.empleadores.nuevo.infra.out.store.BoletaPagoStorage;
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
public class BoletaPagoDetalleImprimirServiceImpl implements BoletaPagoDetalleImprimirService {
	JasperReport boletaJasper;
	JasperReport boletaDdjjJasper;
	
	@Autowired
	private DataSource dataSource;
	 
	private final MessageSource messageSource;
	private final BoletaPagoStorage boletaPagoStorage;
	private final DateTimeProvider dateTimeProvider;
	
	@PostConstruct
	private void init() {
		log.debug("init(): ARRANQUE...");
		//log.debug("*** datasourceUrl: " + datasourceUrl);
		
		try {			
			InputStream fileJasper = getClass().getClassLoader().getResourceAsStream("reportes/boletaPagoDetalle.jrxml");
			boletaJasper = JasperCompileManager.compileReport(fileJasper);
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
		LocalDate vto = reg.get().getVencimiento();

		byte[] pdfBytes = null;
		HashMap<String, Object> params = null;
		 
		try {
	    	params = new HashMap<String, Object>();
	    	
	    	params.put("boletaId", id );	    	
	    	params.put("fechaVencimiento", dateTimeProvider.getDateToString(vto)  );	    	
	    	params.put("logo", "reportes/UOMA_AZUL.png" );
	    	  
	    	JasperPrint print = null;
	    	Connection con = dataSource.getConnection();
    		print = JasperFillManager.fillReport(boletaJasper, params, con);
			pdfBytes = JasperExportManager.exportReportToPdf(print);
			con.close();
		} catch(Exception e) {
			log.error( e.toString() );
			throw e;
		}

	    log.debug( "FIN " );
		return pdfBytes;
		 
	}
	
}
