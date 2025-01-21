package ar.ospim.empleadores.auth.usuario.app.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.CrearTokenClave;
import ar.ospim.empleadores.auth.usuario.app.validator.UsuarioAuthoritiesValidator;
import ar.ospim.empleadores.auth.usuario.app.validator.UsuarioValidator;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaUsuarioStorage;

@Service
public class CrearTokenClaveImpl implements CrearTokenClave {
    private final Logger logger;
    private final MessageSource messageSource;
    
    private final EmpresaUsuarioStorage empresaUsuarioStorage;
    private final UsuarioValidator userValidator;

    public CrearTokenClaveImpl(MessageSource messageSource, EmpresaUsuarioStorage empresaUsuarioStorage,
    		UsuarioAuthoritiesValidator userAuthoritiesValidator) {
		this.logger = LoggerFactory.getLogger(getClass());
		this.messageSource = messageSource;
		this.empresaUsuarioStorage = empresaUsuarioStorage;
		this.userValidator = new UsuarioValidator(messageSource, userAuthoritiesValidator);
	}

    @Override
    public String run(Integer userId) {
        logger.debug("Input -> userId {}", userId);
        userValidator.assertUpdate(userId);
        String result = empresaUsuarioStorage.crearTokenClaveReset(userId);
        logger.debug("Output -> result {}", result);
        return result;
    }
}
