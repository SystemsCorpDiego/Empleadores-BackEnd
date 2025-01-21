package ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.comun.infra.output.dto.IdGeneradoDto;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInternoBO;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.interno.ActualizarPersonaUsuarioLogueado;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.interno.ActualizarUsuarioInterno;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.interno.ConsultarUsuarioInterno;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.interno.CrearUsuarioInterno;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario.dto.UsuarioInternoAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario.dto.UsuarioInternoDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("usuario/interno")
@RequiredArgsConstructor
public class UsuarioInternoController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final ActualizarUsuarioInterno actualizarUsuarioInterno; 
	private final CrearUsuarioInterno crearUsuarioInterno;
	private final ConsultarUsuarioInterno consultarUsuarioInterno;
	private final ActualizarPersonaUsuarioLogueado actualizarPersonaUsuarioLogueado;
	private final UsuarioInternoMapper mapper;
	
	@GetMapping()
	public ResponseEntity<List<UsuarioInternoDto>>  consultar() {
		List<UsuarioInternoBO> lst = consultarUsuarioInterno.run();
		return ResponseEntity.ok( mapper.map(lst) ); 
	}
	
	@PostMapping()
	public ResponseEntity<IdGeneradoDto>  agregar( @RequestBody @Valid UsuarioInternoAltaDto dato, HttpServletRequest request) {
		
		UsuarioInternoBO usuarioInterno = mapper.map(dato);
		UsuarioInternoBO registro = crearUsuarioInterno.run(usuarioInterno);		
		IdGeneradoDto rta = new IdGeneradoDto(registro.getId());
		
		URI location = URI.create( String.format(request.getRequestURI()+"%s", registro.getId()) );
 
		return ResponseEntity.created( location ).body(rta);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void>  actualizar(@PathVariable Integer id, @RequestBody @Valid UsuarioInternoAltaDto dato) {
		logger.debug("id: " + id + " - dato: " + dato.toString());
		
		UsuarioInternoBO registro = mapper.map( id, dato );
		actualizarUsuarioInterno.run(registro);
		
		logger.debug("FIN" );
		return ResponseEntity.noContent().<Void>build(); 
	}
	
	@PutMapping(value = "/persona/logueada")
	public ResponseEntity<Void>  actualizarPersonaLogueada( @RequestBody @Valid UsuarioInternoDto dato ) {
		logger.debug("dato: " + dato.toString());
		
		UsuarioInternoBO registro = mapper.map( dato );
		actualizarPersonaUsuarioLogueado.run(registro);
		
		logger.debug("FIN" );
		return ResponseEntity.noContent().<Void>build(); 
	}
	
}
