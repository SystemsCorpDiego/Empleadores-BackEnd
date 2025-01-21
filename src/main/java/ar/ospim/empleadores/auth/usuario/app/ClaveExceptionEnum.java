package ar.ospim.empleadores.auth.usuario.app;

import lombok.Getter;

@Getter
public enum ClaveExceptionEnum {
	USUARIO_INCORRECTO,
	CLAVE_INCORRECTA,
	CLAVE_NUEVA_INCORRECTA;
	
	public String getMsgKey() {
		return "auth.clave." + this.name();
	}	
}
