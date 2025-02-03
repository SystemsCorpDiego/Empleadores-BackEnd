package ar.ospim.empleadores.nuevo.infra.input.rest.app.publicaciones;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ar.ospim.empleadores.comun.infra.output.dto.IdGeneradoDto;
import ar.ospim.empleadores.nuevo.app.dominio.PublicacionBO;
import ar.ospim.empleadores.nuevo.app.servicios.publicacion.PublicacionService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.publicaciones.dto.PublicacionDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.publicaciones.dto.PublicacionVigenteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("publicaciones")
@RequiredArgsConstructor
public class publicacionController {
	private final PublicacionService service;
	private final PublicacionDtoMapper mapper;
	
	
	@PostMapping
	public ResponseEntity<IdGeneradoDto>  agregar( @RequestBody @Valid PublicacionDto dato, HttpServletRequest request) {
		
		PublicacionBO registro = mapper.map(dato);
		registro = service.crear(registro);		
		IdGeneradoDto rta = new IdGeneradoDto(registro.getId());
		
		URI location = URI.create( String.format(request.getRequestURI()+"%s", registro.getId()) );
 
		return ResponseEntity.created( location ).body(rta);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?>  actualizar(@PathVariable Integer id, @RequestBody @Valid PublicacionDto dato) {
		log.debug("id: " + id + " - dato: " + dato.toString());
		
		PublicacionBO registro = mapper.map(id, dato);
		registro = service.crear(registro);
		
		log.debug("FIN" );
		return ResponseEntity.noContent( ).build();
	}
	
	@DeleteMapping(value = "/{id}")
	public void  borrar( @PathVariable Integer id ) {
		service.borrar(id);
	}
	
	
	@GetMapping
	public ResponseEntity<List<PublicacionDto>>  consultar() {
		List<PublicacionBO> consulta = service.consultar();
		List<PublicacionDto> consultaDto = mapper.map(consulta) ;
		return ResponseEntity.ok( consultaDto );
	}

	//sigeco/publicacionesVigentes  PublicacionVigenteDto
	@GetMapping(value = "/vigentes")
	public ResponseEntity<List<PublicacionVigenteDto>>  consultarVigentes() {
		List<PublicacionBO> consulta = service.consultarVigentes();
		List<PublicacionVigenteDto> consultaDto = mapper.mapVig(consulta) ;
		return ResponseEntity.ok( consultaDto );
	}
	
	
	@PostMapping("/{id}/archivo")
	public ResponseEntity<Void> handleFileUpload(@PathVariable Integer id, @RequestParam("archivo") MultipartFile archivo) {

		service.guardarArchivo(id, archivo);

		return ResponseEntity.ok( null );
	}
	
	@GetMapping("/{id}/archivo")
	public ResponseEntity<Resource>  handleFileDownload(@PathVariable Integer id) {
		 
		Resource file = service.getArchivo(id);

		if (file == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
}
