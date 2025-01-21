package ar.ospim.empleadores.auth.usuario.app;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.dominio.ClaveResetTokenBo;
import ar.ospim.empleadores.auth.usuario.dominio.usuario.modelo.OAuthUsuarioBo;
import ar.ospim.empleadores.auth.usuario.dominio.usuario.servicio.OAuthUsuarioManagementStorage;
import ar.ospim.empleadores.auth.usuario.dominio.usuarioclave.ValidatorClave;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.infra.out.store.ClaveResetTokenStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateUsuarioYClave {

    @Value("${ws.oauth.enabled:false}")
    private boolean oAuthServiceEnabled;
    
    private final MessageSource messageSource;    
    private final UsuarioStorage userStorage;
    private final ClaveResetTokenStorage passwordResetTokenStorage;
    private final UpdateClaveUsuario updateUserPassword;
    private final OAuthUsuarioManagementStorage oAuthUserManagementStorage;
	private final ValidatorClave passwordValidator;

    public void run(String token, String username, String password) {
        log.debug("Input -> username {}", username);
        ClaveResetTokenBo passwordResetTokenBo = passwordResetTokenStorage.get(token);
        UsuarioBO user = updateUsername(username, passwordResetTokenBo.getUsuarioId());
		assertValidUpdate(password);
		updateUserPassword.run(user, password);
        passwordResetTokenStorage.deshabilitarTokens(passwordResetTokenBo.getUsuarioId());
    }

    private UsuarioBO updateUsername(String username, Integer userId) {
    	UsuarioBO user = userStorage.getUsuario(userId);
        if (oAuthServiceEnabled)
            oAuthUserManagementStorage.actualizarUsuario(user.getDescripcion(), new OAuthUsuarioBo(username, null, null, null, null));
        user.setDescripcion(username);
        userStorage.actualizar(user);
        return user;
    }

	private void assertValidUpdate (String password) throws ClaveException {
		if (password == null || password.isEmpty() || !passwordValidator.esValida(password)) {
			String errorMsg = messageSource.getMessage(ClaveExceptionEnum.CLAVE_NUEVA_INCORRECTA.getMsgKey(), null, new Locale("es"));
			throw new ClaveException(ClaveExceptionEnum.CLAVE_NUEVA_INCORRECTA.name(), errorMsg);
		}
	}

}
