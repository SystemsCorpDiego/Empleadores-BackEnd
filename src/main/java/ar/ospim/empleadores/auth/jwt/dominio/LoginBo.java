package ar.ospim.empleadores.auth.jwt.dominio;

import lombok.Getter;

@Getter
public class LoginBo {

	public final String usuario;
	public final String clave;

	public LoginBo(String usuario, String clave) {
		this.usuario = usuario;
		this.clave = clave;
	}

}
