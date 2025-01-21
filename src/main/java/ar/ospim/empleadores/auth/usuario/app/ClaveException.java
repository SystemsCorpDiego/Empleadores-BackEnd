package ar.ospim.empleadores.auth.usuario.app;

import lombok.Getter;

@Getter
public class ClaveException extends RuntimeException {
	public final String codigo;
	public ClaveException(String codigo, String msg) {
		super(msg);
		this.codigo = codigo;
	}
}
