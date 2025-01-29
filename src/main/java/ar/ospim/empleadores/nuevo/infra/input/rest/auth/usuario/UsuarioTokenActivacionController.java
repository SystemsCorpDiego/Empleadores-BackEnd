package ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.auth.usuario.app.UsuarioActivarPorToken;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.UsuarioActivadoDto;

@RestController

@RequestMapping("auth/usuario")
public class UsuarioTokenActivacionController {

	@Autowired
	private UsuarioActivarPorToken service;
	
    @PostMapping(value={"/activar/{token}", "/activar/{token}/"} )
    public ResponseEntity<UsuarioActivadoDto>  activarUsuarioEmpresa (@PathVariable  String token ) {
    	
    	String usuario = service.run(token);
    	UsuarioActivadoDto usuarioDto = new UsuarioActivadoDto();
    	usuarioDto.setUsuario(usuario);
    	
    	return ResponseEntity.ok(usuarioDto);
    }

    @GetMapping("{usuario}/activar/token")
    public ResponseEntity<String>  getToken (@PathVariable  String usuario) {
    	String token = service.crearToken(usuario);
    	return ResponseEntity.ok(token);
    }
    
}


