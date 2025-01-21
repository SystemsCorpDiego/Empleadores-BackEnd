package ar.ospim.empleadores.auth.jwt.app;

import lombok.Getter;

@Getter
public class BadRefreshTokenException extends Exception {
	private String codigo;
	
	public BadRefreshTokenException(String codigo, String msg) {
		super(msg);
		this.codigo = codigo;
	}

}
