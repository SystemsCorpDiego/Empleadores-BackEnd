package ar.ospim.empleadores.auth.jwt.app.login;

import lombok.Getter;

@Getter
public enum LoginEnumException {
	USUARIO_ANONIMO,
	ERROR_CREDENCIAL,
    ERROR_CREDENCIAL_PWD,
    USUARIO_DESHABILITADO,
    USUARIO_EMPRESA_RESTRINGIDA,
	ERROR_TOKEN_REFRESH; //BadRefreshTokenException
	
	
	LoginEnumException() {
    }
	
	public String getMsgKey() {
		return "login.input." + this.name();
	}
}
