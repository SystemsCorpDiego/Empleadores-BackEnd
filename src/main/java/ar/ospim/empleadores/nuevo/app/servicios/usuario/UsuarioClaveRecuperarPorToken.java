package ar.ospim.empleadores.nuevo.app.servicios.usuario;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario.dto.UsuarioClaveRecuperarPorTokenDto;

public interface UsuarioClaveRecuperarPorToken {

	public String runGenToken(String mail);
	
	public UsuarioClaveRecuperarPorTokenDto runConsul(String token);
	
	public void runActualizarClave(String token, String clave);
	
}
