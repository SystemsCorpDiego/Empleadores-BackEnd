package ar.ospim.empleadores.exception;

import ar.ospim.empleadores.comun.exception.TicketRuntimeException;

public class WsException extends TicketRuntimeException {
	
	private static final long serialVersionUID = 5648879119108828799L;

	public WsException(String codigo, Exception e) {
        super(codigo, e);
    }
	
	public WsException(String codigo, String codeMsg, Exception e) {
        super(codigo, codeMsg, e);        
    }
	
	public WsException(String codigo, String message) {
        super(codigo, message);
    }
	

	public String getErrorType() {
		return "ERROR_APP_WS";
	}
		
    public String errorToString() {
		return getErrorType() + " - " + super.errorToString() ; 
	}

}
