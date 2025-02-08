package ar.ospim.empleadores.nuevo.app.servicios.usuario;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario.dto.UsuarioClaveRecuperarPorTokenDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.UsuarioRecuperoClaveTokenDto;

public interface UsuarioClaveRecuperarPorToken {

	public UsuarioRecuperoClaveTokenDto runGenTokenByMail(String mail);
	public UsuarioRecuperoClaveTokenDto runGenTokenByUsuarioDescrip(String descripcion);
	
	public UsuarioClaveRecuperarPorTokenDto runConsul(String token);
	
	public void runActualizarClave(String token, String clave);
	
}
