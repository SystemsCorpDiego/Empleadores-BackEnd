package ar.ospim.empleadores.auth.usuario.app;

import lombok.Getter;

@Getter
public enum OAuthUsuarioEnumException {
    ERROR_GETTING_USER,
    ERROR_CREATING_USER;
	
	public String getMsgKey() {
		return "oauth.usuario." + this.name();
	}	
}
