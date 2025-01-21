package ar.ospim.empleadores.sgx.exceptions;

public class PermissionDeniedException extends RuntimeException {

	public PermissionDeniedException(String message) {
		super(message);
	}
}
