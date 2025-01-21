package ar.ospim.empleadores.exception;

import ar.ospim.empleadores.comun.exception.TicketRuntimeException;

public class ServiceException  extends TicketRuntimeException {
 
	private static final long serialVersionUID = 7459091723673908077L;

	
	public String getErrorType() {
		return "ERROR_APP_SERVICE";
	}

    public String errorToString() {
		return getErrorType() + " - " + super.errorToString() ; 
	}

	public ServiceException(String codigo, Exception e) {
        super(codigo, e);
    }
	
	public ServiceException(String codigo, String codeMsg, Exception e) {
        super(codigo, codeMsg, e);
    }

	public ServiceException(String codigo, String message) {
        super(codigo, message);
    }

	public ServiceException(RepositoryException e) {
        super(e);
    }	
	 
	 
	
}
