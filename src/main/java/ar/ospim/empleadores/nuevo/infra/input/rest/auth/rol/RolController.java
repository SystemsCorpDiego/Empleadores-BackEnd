package ar.ospim.empleadores.nuevo.infra.input.rest.auth.rol;

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

import ar.ospim.empleadores.auth.usuario.app.RolService;
import ar.ospim.empleadores.comun.infra.output.dto.IdGeneradoDto;
import ar.ospim.empleadores.nuevo.app.dominio.RolBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto.IdDescripDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("roles")
@RequiredArgsConstructor
public class RolController {
    private static final Logger LOG = LoggerFactory.getLogger(RolController.class);
	private final RolService service;
	private final RolDtoMapper mapper;
	
	@PostMapping
	public ResponseEntity<IdGeneradoDto>  agregar( @RequestBody @Valid IdDescripDto dato, HttpServletRequest request) {
		
		RolBO registro = mapper.map(dato);
		registro = service.crear(registro);		
		IdGeneradoDto rta = new IdGeneradoDto( registro.getId().intValue() );
		
		URI location = URI.create( String.format(request.getRequestURI()+"%s", registro.getId()) );
 
		return ResponseEntity.created( location ).body(rta);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?>  actualizar(@PathVariable Integer id, @RequestBody @Valid IdDescripDto dato) {
		LOG.debug("id: " + id + " - dato: " + dato.toString());
		
		RolBO registro = mapper.map(id, dato);
		registro = service.crear(registro);
		
		LOG.debug("FIN" );
		return ResponseEntity.noContent( ).build();
	}
	
	@DeleteMapping(value = "/{id}")
	public void  borrar( @PathVariable Short id ) {
		service.borrar(id);
	}
	
	
	@GetMapping("")
	public ResponseEntity<List<IdDescripDto>>  consultar() {
		List<RolBO> consulta = service.consultar();
		List<IdDescripDto> consultaDto = mapper.map(consulta) ;
		return ResponseEntity.ok( consultaDto );
	}

	@GetMapping("/usuario-interno")
	public ResponseEntity<List<IdDescripDto>>  usuarioInternoConsultar() {
		List<RolBO> consulta = service.usuarioInternoConsultar();
		List<IdDescripDto> consultaDto = mapper.map(consulta) ;
		return ResponseEntity.ok( consultaDto );
	}
	
}
