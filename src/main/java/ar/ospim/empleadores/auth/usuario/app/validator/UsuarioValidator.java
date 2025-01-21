package ar.ospim.empleadores.auth.usuario.app.validator;

import java.util.Locale;

import org.springframework.context.MessageSource;

import ar.ospim.empleadores.sgx.exceptions.PermissionDeniedException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioValidator {
	 
    private final MessageSource messageSource;
	
	private final UsuarioAuthoritiesValidator authoritiesValidator; 
    
	public UsuarioValidator(MessageSource messageSource, UsuarioAuthoritiesValidator authoritiesValidator) {
		this.authoritiesValidator = authoritiesValidator;
		this.messageSource = messageSource;
	}
	  
    public void assertUpdate(Integer userId) {
    	//MessageSource messageSource = (ReloadableResourceBundleMessageSource) applicationContext.getBean("messageSource");
        if (authoritiesValidator.isLoggedUserId(userId)) {
        	String errorMsg = messageSource.getMessage("auth.SIN_PERMISO", null, new Locale("es"));
            throw new PermissionDeniedException(errorMsg);
        }
        authoritiesValidator.assertLoggedUserOutrank(userId);
    }
    
}
