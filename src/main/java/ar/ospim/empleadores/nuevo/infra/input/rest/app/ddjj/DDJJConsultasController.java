package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.comun.strings.StringHelper;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJConsultarService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJConsultaFiltroDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJTotalesEmpresaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/ddjj")
@RequiredArgsConstructor
public class DDJJConsultasController {
	
	private final DDJJConsultarService consultarService;
	
	//Consulta de DDJJ para Empledos de OSPIM - Ven todos los CUITs
	@PostMapping(value = "/totales")
	public ResponseEntity<List<DDJJTotalesEmpresaDto>>  consultarDDJJTotales(@RequestBody DDJJConsultaFiltroDto filtro) {
		log.debug( "filtro: " + filtro );		 		 

		List<DDJJTotalesEmpresaDto> lst = consultarService.consultarTotales(filtro);
		
		log.debug("FIN" );
		return ResponseEntity.ok( lst );	 
	}

	@GetMapping(value = "/totales")
	public ResponseEntity<List<DDJJTotalesEmpresaDto>>  consultarDDJJTotales(@Nullable @RequestParam String cuit, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @Nullable @RequestParam LocalDate desde, 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @Nullable @RequestParam LocalDate hasta) {
		log.debug( "cuit: " + cuit );		 
		DDJJConsultaFiltroDto filtro = new DDJJConsultaFiltroDto();
		if ( !StringHelper.isNullOrWhiteSpace(cuit) ) 
			filtro.setCuit(cuit);
		if ( desde != null ) {
			filtro.setDesde(desde);
		}
		if ( hasta != null ) {
			filtro.setHasta(hasta);
		}
		
		List<DDJJTotalesEmpresaDto> lst = consultarService.consultarTotales( filtro );
		
		log.debug("FIN" );
		return ResponseEntity.ok( lst );	 
	}
	
	
	@GetMapping(value="/public/plantilla_download/xls",
			produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<?> downloadFile()  {
		//to consume http://youserver/file_download?filename=mytest
		try {
			System.out.println("in file download " );
			//String path = "src/main/resources/public/DDJJCarga-Plantilla.xls"; //path of your file
			
			
			//Path dirPath = Paths.get("resources/DDJJCarga-Plantilla.xls");
			
			//System.out.println("in file download - dirPath: " + dirPath );
			Resource resource = getResourse("xls");  			
			String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
			String contentType = "application/octet-stream";
			
			InputStream stream = DDJJConsultasController.class.getResourceAsStream("/DDJJCarga-Plantilla.xls");
			
			return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
	                .body(stream.readAllBytes());
			
		} catch(Exception e) {
			System.out.println("error in file_download "+e); return null;
		}
	}
	
	@GetMapping(value="/public/plantilla_download/csv",
			produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<?> downloadFileCsv()  {
		//to consume http://youserver/file_download?filename=mytest
		try {
			System.out.println("in file download " );
			
			//System.out.println("in file download - dirPath: " + dirPath );
			Resource resource = getResourse("csv");  			
			String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
			String contentType = "application/octet-stream";
			
			InputStream stream = DDJJConsultasController.class.getResourceAsStream("/DDJJCarga-Plantilla.csv");
			
			return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
	                .body(stream.readAllBytes());
			
		} catch(Exception e) {
			System.out.println("error in file_download "+e); return null;
		}
	}
	
	private Resource getResourse(String tipo) throws MalformedURLException {
		Path dirPath;
		dirPath = Paths.get("DDJJCarga-Plantilla.csv");
		if( tipo.equals("xls"))
			dirPath = Paths.get("DDJJCarga-Plantilla.xls");
		
		return new UrlResource(dirPath.toUri());
	}
	 
}
