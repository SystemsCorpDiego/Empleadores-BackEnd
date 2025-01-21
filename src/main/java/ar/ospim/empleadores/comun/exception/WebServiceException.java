package ar.ospim.empleadores.comun.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WebServiceException extends TicketRuntimeException {
	private static final long serialVersionUID = 3089598110716516140L;
	
	public WebServiceException(String codigo, String descripcion, Exception e) {
		super(codigo, descripcion, e);
	}
	
	public WebServiceException(String codigo, String descripcion) {
		super(codigo, descripcion);		
	}

    public String getErrorType() {
		return "ERROR_APP_WS";
	}

	@Override
	public String toString() {
		return "WebServiceException [getErrorType()=" + getErrorType() + ", toString()=" + super.toString() + "]";
	}
    

}
