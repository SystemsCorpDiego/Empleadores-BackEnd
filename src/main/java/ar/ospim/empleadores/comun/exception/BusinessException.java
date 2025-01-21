package ar.ospim.empleadores.comun.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include. NON_NULL)
public class BusinessException extends  TicketRuntimeException {


    /**
	 * 
	 */
	private static final long serialVersionUID = 7709267122978167610L;


	public BusinessException(String codigo, String descripcion) {
        super(codigo, descripcion);
    }

    public BusinessException( String descripcion ) {
        super(descripcion);
    }

    
    public String getErrorType() {
		return "ERROR_APP_BUSINESS";
	}
    
      
}
