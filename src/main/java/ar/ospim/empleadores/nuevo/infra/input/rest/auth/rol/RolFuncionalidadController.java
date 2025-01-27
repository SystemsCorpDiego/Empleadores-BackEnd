package ar.ospim.empleadores.nuevo.infra.input.rest.auth.rol;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.auth.RolFuncionalidadService;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.rol.dto.RolFuncionalidadAltaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController //"roles/{rolId}", 
@RequestMapping(value = {"roles/funcionalidades", "funcionalidades"})
@RequiredArgsConstructor
public class RolFuncionalidadController {

	private final RolFuncionalidadService rolFuncionalidadService;
	
	@PutMapping(value = { "/actualizar/{rolId}"})
	public ResponseEntity<?>  actualizar(@PathVariable Short rolId, @RequestBody @Valid RolFuncionalidadAltaDto dato) {
		log.debug("rolId: " + rolId+ " - dato: " + dato.toString());
		rolFuncionalidadService.actualizar(rolId, dato);
		log.debug("FIN" );
		return ResponseEntity.noContent( ).build();
	}
	
	@GetMapping( "/{rolDescrip}" )
	public ResponseEntity<RolFuncionalidadAltaDto> consultarRol(@PathVariable String rolDescrip) {
		RolFuncionalidadAltaDto reg = rolFuncionalidadService.consultarRol(rolDescrip);
		return ResponseEntity.ok(reg) ;	
	}

	@GetMapping( "/usuario" )
	public ResponseEntity<List<RolFuncionalidadAltaDto>> consultarTipoUsuario() {
		List<RolFuncionalidadAltaDto> lst = rolFuncionalidadService.consultarTipoUsuario();
		return ResponseEntity.ok(lst) ;	
	}
	
	@GetMapping( "" )
	public ResponseEntity<List<RolFuncionalidadAltaDto>> consultarTodosRol() {
		List<RolFuncionalidadAltaDto> lst = rolFuncionalidadService.consultarTodosRol();
		return ResponseEntity.ok(lst) ;	
	}

}
