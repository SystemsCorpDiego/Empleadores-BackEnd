package ar.ospim.empleadores.nuevo.app.servicios.ddjj.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJImprimirService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
@RequiredArgsConstructor
public class DDJJImprimirServiceImpl implements DDJJImprimirService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	JasperReport ddjjJasper;	
		
	 @Autowired
	 protected DataSource dataSource;
	
	@PostConstruct
	private void init() {
		logger.debug("init(): ARRANQUE...");
		try {
			
			InputStream fleJasper = getClass().getClassLoader().getResourceAsStream("reportes/ddjj.jrxml");
			ddjjJasper = JasperCompileManager.compileReport(fleJasper);
			//con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sigecoDB", "postgres","Local123");
			//con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","tecnicos","Valkyria01"); //SystemsCorp
				
		}  catch(Exception e) {
			logger.error("init() - ERROR : " +  e.getMessage() +" - "+ e.getCause() +" - "+ e.getStackTrace() );
		}
	 }
	
	
	@Override
	public byte[] run(Integer id)  throws JRException, SQLException {
		byte[] pdfBytes = null;
		//String in = "ar\\molineros\\sigeco\\ddjj.jasper";
		HashMap<String, Object> params = null;
		Long aux = Long.parseLong(String.valueOf(id));  
		//params.put("ddjjId",  new Long( 1 ));
		
	    try {
	    	params = new HashMap<String, Object>();
	    	params.put("ddjjId", aux);
	    	params.put("P_logoPath1", "reportes/UOMA_AZUL.png");
	    	//params.put("P_logoPath2", "reportes/UOMA_AZUL.png");
	    	Connection con = dataSource.getConnection();
			JasperPrint print = JasperFillManager.fillReport(ddjjJasper, params, con);
			pdfBytes = JasperExportManager.exportReportToPdf(print);
			con.close(); 
		} catch(Exception e) {
			logger.error( e.toString() );
			throw e;
		}
	    
		//ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //Base64.Encoder base64Encoder = Base64.getEncoder();
		//base64Img =  new String(base64Encoder.encode(bos.toByteArray()));
		//bos.close();

	    logger.debug( "FIN " );
		return pdfBytes;
	}

}
