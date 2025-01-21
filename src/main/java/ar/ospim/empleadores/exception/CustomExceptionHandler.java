package ar.ospim.empleadores.exception;

import java.util.HashMap;
import java.util.Map;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import ar.ospim.empleadores.auth.jwt.app.login.LoginException;
import ar.ospim.empleadores.auth.usuario.app.ClaveException;
import ar.ospim.empleadores.comun.exception.ApiErrorMessageDto;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.exception.WebServiceException;
import ar.ospim.empleadores.sgx.exceptions.PermissionDeniedException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@NoArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages = "ar.ospim.empleadores")
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);

	private int auxCanti;
	
   
	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	@ExceptionHandler({ LoginException.class })
	protected ApiErrorMessageDto handleBadLoginException(LoginException ex, WebRequest request) {
		log.debug("handleBadLoginException exception -> {}", ex.getMessage());
		BusinessException exB = new BusinessException( null, ex.getMessage() );
		ApiErrorMessageDto error = new ApiErrorMessageDto(exB.getErrorType(), exB.getTicketError(), null, ex.getMessage());
		
		LOG.error("handleBadLoginException - ApiErrorMessageDto -> {}", error);
		
		return error;
	}
	
	
	@ExceptionHandler({ BusinessException.class })
	public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
		LOG.debug("BusinessException exception -> {}", ex.getDescripcion());

		HttpStatus status = HttpStatus.PRECONDITION_FAILED;
		if ( CommonEnumException.REGISTRO_INEXISTENTE.name().equals( ex.getCodigo() ) )
			status = HttpStatus.NOT_FOUND;
		
		ApiErrorMessageDto error = new ApiErrorMessageDto(ex.getErrorType(), ex.getTicketError(), ex.getCodigo(), ex.getDescripcion());
		
		LOG.error("handleBusinessException - ApiErrorMessageDto -> {}", error);
		
		return ResponseEntity.status(status).body(error);
	}
	
	
	@ExceptionHandler({ WebServiceException.class })
	public ResponseEntity<Object> handleWebServiceException(WebServiceException ex, WebRequest request) {
		LOG.debug("handleWebServiceException exception -> {}", ex.toString() );

		HttpStatus status = HttpStatus.PRECONDITION_FAILED; 
		
		ApiErrorMessageDto error = new ApiErrorMessageDto(ex.getErrorType(), ex.getTicketError(), ex.getCodigo(), "El Webservice no pudo ejecutarse con exito");
		
		LOG.error("handleWebServiceException - ApiErrorMessageDto -> {}", error);
		
		return ResponseEntity.status(status).body(error);
	}	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ ClaveException.class })
	protected ApiErrorMessageDto handleInvalidPasswordException(ClaveException ex) {
		LOG.debug("InvalidUserException -> {}", ex.getMessage(), ex);
		BusinessException exB = new BusinessException( null, ex.getMessage() );
		
		ApiErrorMessageDto error = new ApiErrorMessageDto(exB.getErrorType(), exB.getTicketError(), null, ex.getMessage());
		LOG.error("handleInvalidPasswordException - ApiErrorMessageDto -> {}", error);

		return error;
	}

	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	@ExceptionHandler({ PermissionDeniedException.class })
	public ApiErrorMessageDto handleConstraintViolation(PermissionDeniedException ex, WebRequest request) {
		ApiErrorMessageDto error = new ApiErrorMessageDto(null, ex.getMessage());

		LOG.error("handleConstraintViolation - ApiErrorMessageDto -> {}", error);
		
		return error;
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {		
		log.debug("handleMethodArgumentNotValid - INIT - {}", ex);
		
		CustomException ticket = new CustomException(ExceptionEnum.APP_USE.getCodigo(), "Metodo o Argumentos Invalidos");
		BusinessException bTicket = new BusinessException(ExceptionEnum.APP_USE.getCodigo().toString(), "Metodo o Argumentos Invalidos");
		
		Map<String, String> errors = new HashMap<>();
		this.auxCanti=1;
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			if (error instanceof FieldError) {
				String fieldName = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage();
				if ( errors.containsKey(fieldName) ) {
					errors.put(fieldName + "_" + this.auxCanti, errorMessage);
					this.auxCanti++;
				} else {
					errors.put(fieldName, errorMessage);
				}
			} else {
				errors.put(error.getObjectName(), error.getDefaultMessage());				
			}
		});

		ticket.setDescripcion("Errores de argumentos: " );
		if (errors != null) {
			ticket.setDescripcion( ticket.getDescripcion() + " [" + errors.toString() + "]");
		}
		ticket.setErrores(errors);
		bTicket.setDescripcion(ticket.getDescripcion());
		
		ApiErrorMessageDto error = new ApiErrorMessageDto(bTicket.getErrorType(), bTicket.getTicketError(), null, bTicket.getDescripcion());
		
		log.error("handleMethodArgumentNotValid -  ApiErrorMessageDto: {}", error );
		return ResponseEntity.status(status).body(error);
	}	 
	

	@Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
	        HttpMessageNotReadableException ex, HttpHeaders headers,
	        HttpStatus status, WebRequest request) {
        // paste custom hadling here
		
		log.debug("handleHttpMessageNotReadable - INIT - {}", ex);
		
		CustomException ticket = new CustomException(ExceptionEnum.APP_USE.getCodigo(), "Metodo o Argumentos Invalidos");
		BusinessException bTicket = new BusinessException(ExceptionEnum.APP_USE.getCodigo().toString(), "Metodo o Argumentos Invalidos");
		
		Throwable cause = ex.getCause();
		 
		Map<String, String> errors = new HashMap<>();
		 
		try {
			if ( cause instanceof MismatchedInputException ) {
				ticket.setDescripcion( ex.getMessage() );
			}
			if (cause instanceof UnrecognizedPropertyException) {
				
				ticket.setDescripcion("");
				for(JsonMappingException.Reference error : ((UnrecognizedPropertyException) cause).getPath() ) {
					ticket.setDescripcion( ticket.getDescripcion()+"."+error.getFieldName() );
				}
				errors.put(ticket.getDescripcion(), "propiedad inexistente" );
				ticket.setErrores(errors);
				
				ticket.setDescripcion("propiedad inexistente: " + ticket.getDescripcion() );
				
			} else {
				if (cause instanceof JsonMappingException) { 
					ticket.setDescripcion("");
					if ( ( (JsonMappingException) cause).getCause() != null ) {
						
						for(JsonMappingException.Reference error : ((JsonMappingException) cause).getPath()) {
							if ( error.getFieldName() != null ) {
								ticket.setDescripcion( ticket.getDescripcion()+"."+error.getFieldName() );
							} else {
								if ( error.toString().indexOf("java.util.ArrayList") > -1 ) {
									ticket.setDescripcion( ticket.getDescripcion()+"[]" );
								}
							}
						}
						errors.put(ticket.getDescripcion(), ((JsonMappingException) cause).getCause().getMessage() );
						ticket.setErrores(errors);
						
						ticket.setDescripcion( ticket.getDescripcion() + ": " + ((JsonMappingException) cause).getCause().getMessage()  );
						
					} else {
						if ( cause instanceof InvalidFormatException ) {
							for(JsonMappingException.Reference error : ((InvalidFormatException) cause).getPath()) {
								ticket.setDescripcion( ticket.getDescripcion()+"."+error.getFieldName() );
							}
							errors.put(ticket.getDescripcion(), "El valor informado (" + ((InvalidFormatException) cause).getValue() + ") no pudo convertirse al tipo de dato '" + ((InvalidFormatException) cause).getTargetType() + "'." );
							ticket.setErrores(errors);
							ticket.setDescripcion( ticket.getDescripcion() +":"+ errors.get( ticket.getDescripcion() ));
						} else {
							ticket.setDescripcion(  ( (JsonMappingException) cause).getMessage() );
						}
					}					
				} else { 				 
					if ( ex.getCause().getMessage() != null) {
						ticket.setDescripcion( ex.getCause().getMessage() );
					} else {
						ticket.setDescripcion( ex.getMessage() );
					}
				}				
			}				
		} catch (Exception e) {
			ticket.setDescripcion("Error no esperado: " + ex.getMessage());
		}
		bTicket.setDescripcion(ticket.getDescripcion());
		ticket.setErrores(errors);

		ApiErrorMessageDto error = new ApiErrorMessageDto(bTicket.getErrorType(), bTicket.getTicketError(), null, ex.getMessage());
		
		log.error("handleHttpMessageNotReadable -  OUTPUT: {}", ticket );			
		log.error("handleHttpMessageNotReadable -  ApiErrorMessageDto: {}", error );
		return ResponseEntity.badRequest().body(error);	
    }
	
	
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ RepositoryException.class })
	public ApiErrorMessageDto handleRepositoryException(RepositoryException ex) {
		
		log.error("handleRepositoryException - INIT - {}", ex);
		ApiErrorMessageDto error = new ApiErrorMessageDto(ex.getErrorType(), ex.getTicketError(), ""+ex.getCodigo(), ex.getDescripcion());
		log.error("handleRepositoryException - ApiErrorMessageDto: {}", error);
		
		return error;
	}

	
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ PSQLException.class })
	public ApiErrorMessageDto handlePSQLException(PSQLException ex, WebRequest request) {
 		
		log.error("handlePSQLException - INIT - {}", ex);
		
		RepositoryException errorTicket = new RepositoryException( Integer.toString(ex.getErrorCode()) , ex.getLocalizedMessage(), ex);  
		//Error grave. Se ofusca de cara al front-end y se loguea 
		//TODO: Se enviaria mail a una casilla de HelpDesk ??
		
		log.error("handlePSQLException - Error - {}", errorTicket.errorToString() );
		ApiErrorMessageDto error = new ApiErrorMessageDto(errorTicket.getErrorType(), errorTicket.getTicketError(), ""+ex.getErrorCode(), ex.getMessage());
		log.error("handlePSQLException - ApiErrorMessageDto - {}", error );
		
		
		return error;
	}
}
