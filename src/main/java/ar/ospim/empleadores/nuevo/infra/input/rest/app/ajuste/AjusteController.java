package ar.ospim.empleadores.nuevo.infra.input.rest.app.ajuste;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.comun.infra.output.dto.IdGeneradoDto;
import ar.ospim.empleadores.nuevo.app.dominio.AjusteBO;
import ar.ospim.empleadores.nuevo.app.servicios.ajuste.AjusteService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ajuste.dto.AjusteDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ajustes")
@RequiredArgsConstructor 
public class AjusteController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final AjusteService service;
	private final AjusteDtoMapper mapper;

	@GetMapping
	public ResponseEntity<List<AjusteDto>>  consultar() {
		List<AjusteBO> consulta = service.consultar();
		List<AjusteDto> consultaDto = mapper.map(consulta) ;
		 
		return ResponseEntity.ok( consultaDto );
	}
	
	//AjusteService
	@GetMapping("/crud")
	public ResponseEntity<List<AjusteDto>>  consultarCrud() {
		List<AjusteBO> consulta = service.consultarCrud();
		List<AjusteDto> consultaDto = mapper.map(consulta) ;
		 
		return ResponseEntity.ok( consultaDto );
	}
	
	@PostMapping
	public ResponseEntity<IdGeneradoDto>  agregar( @RequestBody @Valid AjusteDto dato, HttpServletRequest request) {
		
		AjusteBO registro = mapper.map(dato);
		registro = service.guardar(registro);		
		IdGeneradoDto rta = new IdGeneradoDto(registro.getId());
		
		URI location = URI.create( String.format(request.getRequestURI()+"%s", registro.getId()) );
 
		return ResponseEntity.created( location ).body(rta);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void>  actualizar(@PathVariable Integer id, @RequestBody @Valid AjusteDto dato) {
		logger.debug("id: " + id + " - dato: " + dato.toString());
		
		AjusteBO registro = mapper.map(dato, id);
		registro = service.guardar(registro);
		
		logger.debug("FIN" );
		return ResponseEntity.noContent().<Void>build(); 
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void>  borrar( @PathVariable Integer id ) {
		service.borrar(id);
		return ResponseEntity.noContent().<Void>build(); 
	}
	
}
