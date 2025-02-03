package ar.ospim.empleadores.nuevo.infra.input.rest.app.AfipInteres;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import ar.ospim.empleadores.nuevo.app.dominio.AfipInteresBO;
import ar.ospim.empleadores.nuevo.app.servicios.afipinteres.AfipInteresService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/afip/intereses")
@RequiredArgsConstructor 
public class AfipInteresController {
	private final AfipInteresService service;
	private final AfipInteresDtoMapper mapper;

	@GetMapping
	public ResponseEntity<List<AfipInteresDto>>  consultar() {
		List<AfipInteresBO> consulta = service.consultar();
		List<AfipInteresDto> consultaDto = mapper.map(consulta) ;
		return ResponseEntity.ok( consultaDto );
	}
	
	@PostMapping
	public ResponseEntity<IdGeneradoDto>  agregar( @RequestBody @Valid AfipInteresDto dato, HttpServletRequest request) {
		 
		AfipInteresBO registro = mapper.map(dato);
		registro = service.guardar(registro);		
		IdGeneradoDto rta = new IdGeneradoDto(registro.getId());
		
		URI location = URI.create( String.format(request.getRequestURI()+"%s", registro.getId()) );
 
		return ResponseEntity.created( location ).body(rta);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void>  actualizar(@PathVariable Integer id, @RequestBody @Valid AfipInteresDto dato) {
		log.debug("id: " + id + " - dato: " + dato.toString());
		
		AfipInteresBO registro = mapper.map(dato, id);
		registro = service.guardar(registro);
		
		log.debug("FIN" );
		return ResponseEntity.noContent().<Void>build(); 
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void>  borrar( @PathVariable Integer id ) {
		service.borrar(id);
		return ResponseEntity.noContent().<Void>build(); 
	}
}
