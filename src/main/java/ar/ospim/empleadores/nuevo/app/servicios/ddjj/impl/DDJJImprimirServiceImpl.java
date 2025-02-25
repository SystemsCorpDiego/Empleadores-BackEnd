package ar.ospim.empleadores.nuevo.app.servicios.ddjj.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.nuevo.app.dominio.AporteBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoAporteBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoBO;
import ar.ospim.empleadores.nuevo.app.servicios.aporte.AporteService;
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
	
	@Autowired
	private final AporteService aporteService;
	
	@Autowired
	private DateTimeProvider dtProvider;
	
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
	    	params.put("periodo",  dtProvider.getPeriodoToString( ddjj.getPeriodo() ));
	    	params.put("secuencia", ddjj.getSecuencia() );
	    	if ( ddjj.getFechaPresentacion() != null)
	    		params.put("presentada_fecha", dtProvider.getDateToString(ddjj.getFechaPresentacion()) );
	    	
	    	params.put("razon_social", ddjj.getEmpresa().getRazonSocial());
	    	params.put("cuit", ddjj.getEmpresa().getCuit());
	    	
	    	List<ddjjPrintDto> lst = getDatos(ddjj);
	    	//Arrays.sort(lst);
	    	Collections.sort(lst);
	    	
	    	params = setTitulosColumnas(lst, params);
	    	
	    	JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource( lst );
	    	
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

	private  List<ddjjPrintDto> getDatos(DDJJBO ddjj) {
		List<ddjjPrintDto> lst = map(ddjj);
    	List<AporteBO> lstAportes = aporteService.consultarOrderByOrdenAsc();	
    	Integer aporteNro = 1;
    	for (AporteBO aporte : lstAportes) {
    		if ( aporte.getDdjj() ) {
    			AporteBO aporteConDatos = procesarAporte( aporte, aporteNro, lst, ddjj);
    			if ( aporteConDatos != null) 
    				aporteNro = aporteNro+1;
    		}
    	}
    	
    	return lst;
	}
	
	private AporteBO procesarAporte(AporteBO aporte, Integer aporteNro, List<ddjjPrintDto> lstPrintDto, DDJJBO ddjj) {
		BigDecimal importeAporte;
		Boolean conDatos = false;
		for (ddjjPrintDto dto:  lstPrintDto) {
			importeAporte =  procesarAporteCuil(aporte, dto.getCuil(), ddjj);
			if ( importeAporte != null ) {
				conDatos = true;
				setImporteAporteCuil(aporte, aporteNro, importeAporte,  dto);
			}
		}
		
		if ( conDatos )
			return aporte;
		
		return null;
	}
	
	private BigDecimal procesarAporteCuil(AporteBO aporte, String cuil, DDJJBO ddjj) {
		for (DDJJEmpleadoBO emple : ddjj.getEmpleados()) {
			if ( emple.getAfiliado().getCuil().equals(cuil) ) {
				for ( DDJJEmpleadoAporteBO aporteCuil : emple.getAportes() ) {
					if ( aporteCuil.getAporte().getCodigo().equals( aporte.getCodigo() )  ) {
						return aporteCuil.getImporte();
					}
				}
			}
		}
		return null;
	}
	
	private void setImporteAporteCuil(AporteBO aporte, Integer aporteNro, BigDecimal importeAporte,  ddjjPrintDto dto) {
		if ( aporteNro.equals(1) ) {
			dto.setAporte1( aporte.getCodigo());
			dto.setAporteDescrip1( aporte.getDescripcion() );
			dto.setImporte1(importeAporte);
		}
		if ( aporteNro.equals(2) ) {
			dto.setAporte2( aporte.getCodigo());
			dto.setAporteDescrip2( aporte.getDescripcion() );
			dto.setImporte2(importeAporte);
		}
		if ( aporteNro.equals(3) ) {
			dto.setAporte3( aporte.getCodigo());
			dto.setAporteDescrip3( aporte.getDescripcion() );
			dto.setImporte3(importeAporte);
		}
		if ( aporteNro.equals(4) ) {
			dto.setAporte4( aporte.getCodigo());
			dto.setAporteDescrip4( aporte.getDescripcion() );
			dto.setImporte4(importeAporte);
		}
		if ( aporteNro.equals(5) ) {
			dto.setAporte5( aporte.getCodigo());
			dto.setAporteDescrip5( aporte.getDescripcion() );
			dto.setImporte5(importeAporte);
		}
		
	}
	
	private HashMap<String, Object> setTitulosColumnas(List<ddjjPrintDto> lstReporte, HashMap<String, Object> params) {
		
		for ( ddjjPrintDto reg : lstReporte) {
			
			if ( reg.getAporteDescrip1() != null )
				params.put("aporte-1", reg.getAporteDescrip1() );
			if ( reg.getAporteDescrip2() != null )
				params.put("aporte-2", reg.getAporteDescrip2() );
			if ( reg.getAporteDescrip3() != null )
				params.put("aporte-3", reg.getAporteDescrip3() );
			if ( reg.getAporteDescrip4() != null )
				params.put("aporte-4", reg.getAporteDescrip4() );
			if ( reg.getAporteDescrip5() != null )
				params.put("aporte-5", reg.getAporteDescrip5() );
			if ( reg.getAporteDescrip6() != null )
				params.put("aporte-6", reg.getAporteDescrip6() );
		}
		
		return params;
	}

	private List<ddjjPrintDto> map(DDJJBO ddjj) {
		List<ddjjPrintDto> rta = new ArrayList<ddjjPrintDto>();
		ddjjPrintDto regNew = null;
		if ( ddjj != null && ddjj.getEmpleados() != null && ddjj.getEmpleados().size() > 0 ) {
			for ( DDJJEmpleadoBO reg : ddjj.getEmpleados()) {
				regNew = new ddjjPrintDto();
				regNew.setCuil(reg.getAfiliado().getCuil());
				regNew.setApellidonombre( reg.getAfiliado().getApellido() + " " + reg.getAfiliado().getNombre() );
				regNew.setIngreso( dtProvider.getDateToString( reg.getIngreso() )  );
				
				regNew.setCamara( reg.getCamara());
				regNew.setCategoria( reg.getCategoria() );
				regNew.setPlanta( reg.getEmpresaDomicilio().getPlanta() );
				regNew.setRemunerativo( reg.getRemunerativo() );
				regNew.setNo_remunerativo( reg.getNoRemunerativo() );
				
				rta.add(regNew);
			}
		}
		
		return rta; 
	}
}
