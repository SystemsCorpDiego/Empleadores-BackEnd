package ar.ospim.empleadores.exception;

import ar.ospim.empleadores.comun.exception.TicketRuntimeException;

public class RepositoryException  extends TicketRuntimeException {
	
	private static final long serialVersionUID = 4521060621999373484L;

	public RepositoryException(String codigo, Exception e) {
        super(codigo, e);         
    }
	
	public RepositoryException(String codigo, String codeMsg, Exception e) {
        super(codigo, codeMsg, e);
    }
	
    public RepositoryException(String codigo, String descripcion) {
        super(codigo, descripcion);
    }

    public String getErrorType() {
		return "ERROR_APP_REPOSITORY";
	}
    
    public String errorToString() {
		return getErrorType() + " - " + super.errorToString() ; 
	}

}
