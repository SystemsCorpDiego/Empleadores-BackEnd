package ar.ospim.empleadores.auth.jwt.app.logindfa;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.jwt.app.generateToken.GenerateToken;
import ar.ospim.empleadores.auth.jwt.dominio.JWTokenBo;
import ar.ospim.empleadores.auth.jwt.dominio.usuario.UsuarioInfoBo;
import ar.ospim.empleadores.auth.jwt.dominio.usuario.UsuarioInfoStorage;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.seguridad.UsuarioInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginDFAImpl  implements LoginDFA {
	private final MessageSource messageSource;
	private final DFAStorage twoFactorAuthenticationStorage;
	private final UsuarioInfoStorage userInfoStorage;
	private final GenerateToken generateToken;

	@Override
	public JWTokenBo execute(String code) {
		log.debug("Input parameter -> code {}", code);
		Integer userId = UsuarioInfo.getCurrentAuditor();
		UsuarioInfoBo userInfo = userInfoStorage.getUsuario(userId);

		if (!this.twoFactorAuthenticationStorage.verifyCode(code)) {
			String errorMsg = messageSource.getMessage(BadOTPExceptionEnum.CODIGO_INVALIDO.getMsgKey(), null, new Locale("es"));	
			throw new BusinessException(BadOTPExceptionEnum.CODIGO_INVALIDO.name(), errorMsg);
		}

		JWTokenBo result = generateToken.generateTokens(userInfo.getId(), userInfo.getNombre());
		userInfoStorage.actualizarLoginDate(userInfo.getNombre());
		return result;
	}
}
