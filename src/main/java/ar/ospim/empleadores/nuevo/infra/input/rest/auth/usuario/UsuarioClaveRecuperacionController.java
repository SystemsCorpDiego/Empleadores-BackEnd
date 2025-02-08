package ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.servicios.usuario.UsuarioClaveRecuperarPorToken;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario.dto.UsuarioClaveRecuperarPorTokenDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.CambiarClaveTokenDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.UsuarioDescripDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.UsuarioMailDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.UsuarioRecuperoClaveTokenDto;

@RestController
@RequestMapping("auth/usuario")
public class UsuarioClaveRecuperacionController {

	@Autowired
	private UsuarioClaveRecuperarPorToken service;
	
	
	@PostMapping(value={"/recupera-clave/mail/token"} )
    public ResponseEntity<UsuarioRecuperoClaveTokenDto>  getToken (@RequestBody  UsuarioMailDto dto) {
		UsuarioRecuperoClaveTokenDto rtaDto = service.runGenTokenByMail(dto.getMail());
    	return ResponseEntity.ok(rtaDto);
    }
	
	@PostMapping(value={"/recupera-clave/usuario/token"} )
    public ResponseEntity<UsuarioRecuperoClaveTokenDto>  getToken (@RequestBody  UsuarioDescripDto dto) {
		UsuarioRecuperoClaveTokenDto rtaDto = service.runGenTokenByUsuarioDescrip(dto.getDescripcion());
    	return ResponseEntity.ok(rtaDto);
    }
	
	@GetMapping(value={"/recupera-clave/token/{token}"} )
    public ResponseEntity<UsuarioClaveRecuperarPorTokenDto>  getTokenInfo (@PathVariable  String token) {
		UsuarioClaveRecuperarPorTokenDto dto = service.runConsul(token);
    	return ResponseEntity.ok(dto);
    }
	
	@PostMapping(value={"/recupera-clave/token/registrar-clave"} )
    public ResponseEntity<Boolean>  actualizarClave (@RequestBody  CambiarClaveTokenDto dto) {
		service.runActualizarClave(dto.getToken(), dto.getClave());		
    	return ResponseEntity.ok(true);
    }
	
}
