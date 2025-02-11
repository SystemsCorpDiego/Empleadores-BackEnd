package ar.ospim.empleadores.nuevo.app.servicios.ddjj.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoBO;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJConsultarService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJImprimirService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.ddjjPrintDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Slf4j
@Service
@RequiredArgsConstructor
public class DDJJImprimirServiceImpl implements DDJJImprimirService {
	
	JasperReport ddjjJasper;	
	JasperReport ddjjJasper2;	
		
	@Autowired
	protected DataSource dataSource;
	
	@Autowired
	private final DDJJConsultarService ddjjService;
	
	@PostConstruct
	private void init() {
		log.debug("init(): ARRANQUE...");
		try {
			
			InputStream fleJasper = getClass().getClassLoader().getResourceAsStream("reportes/ddjj.jrxml");
			ddjjJasper = JasperCompileManager.compileReport(fleJasper);

			InputStream fleJasper2 = getClass().getClassLoader().getResourceAsStream("reportes/ddjjNew.jrxml");
			ddjjJasper2 = JasperCompileManager.compileReport(fleJasper2);
			
			//con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sigecoDB", "postgres","Local123");
			//con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","tecnicos","Valkyria01"); //SystemsCorp
				
		}  catch(Exception e) {
			log.error("init() - ERROR : " +  e.getMessage() +" - "+ e.getCause() +" - "+ e.getStackTrace() );
		}
	 }
	
	@Override
	public byte[] run2(Integer id)  throws JRException, SQLException {
		byte[] pdfBytes = null;
		HashMap<String, Object> params = null;
		Long aux = Long.parseLong(String.valueOf(id));  
	    try {
	    	DDJJBO ddjj = ddjjService.consultar(id);
	    	
	    	
	    	params = new HashMap<String, Object>();
	    	params.put("P_logoPath1", "reportes/UOMA_AZUL.png");
	    	params.put("periodo", "01/2025");
	    	params.put("secuencia", ddjj.getSecuencia() );
	    	params.put("presentada_fecha", "15/01/2024");
	    	params.put("razon_social", ddjj.getEmpresa().getRazonSocial());
	    	params.put("cuit", ddjj.getEmpresa().getCuit());
	    	
	    	JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource( map(ddjj) );
	    	
	    	JasperPrint jasperPrint = JasperFillManager.fillReport(ddjjJasper2, params, dataSource);
	    	pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
	    	
		} catch(Exception e) {
			log.error( e.toString() );
			throw e;
		}
	    
	    log.debug( "FIN " );
		return pdfBytes;
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
			log.error( e.toString() );
			throw e;
		}
	    
		//ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //Base64.Encoder base64Encoder = Base64.getEncoder();
		//base64Img =  new String(base64Encoder.encode(bos.toByteArray()));
		//bos.close();

	    log.debug( "FIN " );
		return pdfBytes;
	}

	
	private List<ddjjPrintDto> map(DDJJBO ddjj) {
		List<ddjjPrintDto> rta = new ArrayList<ddjjPrintDto>();
		ddjjPrintDto regNew = null;
		if ( ddjj != null && ddjj.getEmpleados() != null && ddjj.getEmpleados().size() > 0 ) {
			for ( DDJJEmpleadoBO reg : ddjj.getEmpleados()) {
				regNew = new ddjjPrintDto();
				regNew.setCuil(reg.getAfiliado().getCuil());
				regNew.setApellidonombre( reg.getAfiliado().getApellido() + " " + reg.getAfiliado().getNombre() );
				regNew.setIngreso( reg.getIngreso().toString()  );
				
				rta.add(regNew);
			}
		}
		
		return rta; 
	}
}
