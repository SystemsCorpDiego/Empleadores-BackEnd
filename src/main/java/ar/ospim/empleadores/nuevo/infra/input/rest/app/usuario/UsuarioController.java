package ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.auth.usuario.app.DeshabilitarUsuario;
import ar.ospim.empleadores.auth.usuario.app.HabilitarUsuario;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("usuario")
@RequiredArgsConstructor
public class UsuarioController {
	private final HabilitarUsuario habilitarUsuario; 
	private final DeshabilitarUsuario deshabilitarUsuario;

	@PatchMapping(value = "/descripcion/{descripcion}/habilitar")
	public ResponseEntity<Void>  habilitar( @PathVariable("descripcion") String usuarioNombre ) {
		habilitarUsuario.run(usuarioNombre);
		return ResponseEntity.noContent().<Void>build(); 
	}
	
	@PatchMapping(value = "/descripcion/{descripcion}/deshabilitar")
	public ResponseEntity<Void>  deshabilitar( @PathVariable("descripcion")  String usuarioNombre ) {
		deshabilitarUsuario.run(usuarioNombre);
		return ResponseEntity.noContent().<Void>build(); 
	}

	@PatchMapping(value = "/{id}/habilitar")
	public ResponseEntity<Void>  habilitar( @PathVariable("id") Integer id ) {
		habilitarUsuario.run(id);
		return ResponseEntity.noContent().<Void>build(); 
	}
	
	@PatchMapping(value = "/{id}/deshabilitar")
	public ResponseEntity<Void>  deshabilitar( @PathVariable("id")  Integer id ) {
		deshabilitarUsuario.run(id);
		return ResponseEntity.noContent().<Void>build(); 
	}

}
