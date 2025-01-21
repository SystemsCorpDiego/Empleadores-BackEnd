package ar.ospim.empleadores.auth.usuario.app;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.dominio.clave.ClaveEncriptador;
import ar.ospim.empleadores.auth.usuario.dominio.usuarioclave.ValidatorClave;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UpdateClavePropia {

	private final MessageSource messageSource;
	private final UsuarioStorage userStorage;
	private final ClaveEncriptador passwordEncryptor;
	private final UpdateClaveUsuario updateUserPassword;
	private final ValidatorClave passwordValidator;

	public void execute(Integer userId, String oldPassword, String newPassword) {
		UsuarioBO user = userStorage.getUsuario(userId);
		assertValidUpdate(userId, oldPassword, newPassword, user.getClave());
		updateUserPassword.run(user, newPassword);
	}

	private void assertValidUpdate (Integer userId, String password, String newPassword, String oldPassword) throws  ClaveException{
		if (userId == null) {
			String errorMsg = messageSource.getMessage(ClaveExceptionEnum.USUARIO_INCORRECTO.getMsgKey(), null, new Locale("es"));	
			throw new ClaveException(ClaveExceptionEnum.USUARIO_INCORRECTO.name(), errorMsg);
		}
		if (!passwordEncryptor.iguales(password, oldPassword)) {
			String errorMsg = messageSource.getMessage(ClaveExceptionEnum.CLAVE_INCORRECTA.getMsgKey(), null, new Locale("es"));	
			throw new ClaveException(ClaveExceptionEnum.CLAVE_INCORRECTA.name(), errorMsg);
		}
		if (newPassword == null || newPassword.isEmpty() || !passwordValidator.esValida(newPassword)) {
			String errorMsg = messageSource.getMessage(ClaveExceptionEnum.CLAVE_NUEVA_INCORRECTA.getMsgKey(), null, new Locale("es")); 	
			throw new ClaveException(ClaveExceptionEnum.CLAVE_NUEVA_INCORRECTA.name(), errorMsg);
		}
	}


}
