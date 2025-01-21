package ar.ospim.empleadores.auth.usuario.app.impl;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.ActualizarClaveUsuario;
import ar.ospim.empleadores.auth.usuario.app.ClaveException;
import ar.ospim.empleadores.auth.usuario.app.ClaveExceptionEnum;
import ar.ospim.empleadores.auth.usuario.app.ResetearClave;
import ar.ospim.empleadores.auth.usuario.dominio.ClaveResetTokenBo;
import ar.ospim.empleadores.auth.usuario.dominio.usuarioclave.ValidatorClave;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.infra.out.store.ClaveResetTokenStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ResetearClaveImpl  implements ResetearClave {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MessageSource messageSource;
    private final UsuarioStorage userStorage;
    private final ActualizarClaveUsuario updateUserPassword;
    private final ClaveResetTokenStorage passwordResetTokenStorage;
	private final ValidatorClave passwordValidator;


    @Override
    public String execute(String token, String password) {
        logger.debug("Reset Clave del token -> {}", token);

        ClaveResetTokenBo passwordResetTokenBo = passwordResetTokenStorage.get(token);

        UsuarioBO user = userStorage.getUsuario(passwordResetTokenBo.getUsuarioId());
		assertValidUpdate(password);
		updateUserPassword.run(user, password);
        passwordResetTokenStorage.deshabilitarTokens(passwordResetTokenBo.getUsuarioId());
        return user.getDescripcion();
    }

	private void assertValidUpdate (String password) throws ClaveException {
		if (password == null || password.isEmpty() || !passwordValidator.esValida(password)) {
			String errorMsg = messageSource.getMessage(ClaveExceptionEnum.CLAVE_NUEVA_INCORRECTA.getMsgKey(), null, new Locale("es"));
			throw new ClaveException(ClaveExceptionEnum.CLAVE_NUEVA_INCORRECTA.name(), errorMsg);
		}
	}
	
}
