package ar.ospim.empleadores.auth.jwt.app.login;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.jwt.app.GeneratePartiallyAuthenticationToken;
import ar.ospim.empleadores.auth.jwt.app.generateToken.GenerateToken;
import ar.ospim.empleadores.auth.jwt.dominio.JWTokenBo;
import ar.ospim.empleadores.auth.jwt.dominio.LoginBo;
import ar.ospim.empleadores.auth.jwt.dominio.usuario.UsuarioInfoBo;
import ar.ospim.empleadores.auth.jwt.dominio.usuario.UsuarioInfoStorage;
import ar.ospim.empleadores.auth.usuario.app.ObtenerUsuarioConDFAHabilitado;
import ar.ospim.empleadores.auth.usuario.dominio.clave.ClaveEncriptador;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaRestringidaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class LoginJWTImpl  implements Login {
	
	private final MessageSource messageSource;
	
	private final UsuarioInfoStorage usuarioInfoStorage;

	private final EmpresaRestringidaService empresaRestringidaService;
	
	private final ClaveEncriptador passwordEncryptor;

	private final GenerateToken generateToken;

	private final GeneratePartiallyAuthenticationToken generatePartiallyAuthenticationToken;

	private final ObtenerUsuarioConDFAHabilitado fetchUserHasTwoFactorAuthenticationEnabled;

	
	@Override
	public JWTokenBo execute(LoginBo login) throws LoginException {
		UsuarioInfoBo user = usuarioInfoStorage.getUsuario(login.usuario);
		
		if (user == null) {
			
			if ( empresaRestringidaService.esRestringido(login.usuario) ) {
				String errorMsg = messageSource.getMessage(LoginEnumException.USUARIO_EMPRESA_RESTRINGIDA.getMsgKey(), null, new Locale("es"));			
				throw new LoginException(LoginEnumException.USUARIO_EMPRESA_RESTRINGIDA.name(), String.format(errorMsg, login.usuario) );
			} 
			
			String errorMsg = messageSource.getMessage(LoginEnumException.ERROR_CREDENCIAL.getMsgKey(), null, new Locale("es"));			
			throw new LoginException(LoginEnumException.ERROR_CREDENCIAL.name(), errorMsg);
		}
		if (!user.isHabilitado()) {
			String errorMsg = messageSource.getMessage(LoginEnumException.USUARIO_DESHABILITADO.getMsgKey(), null, new Locale("es"));			
			throw new LoginException(LoginEnumException.USUARIO_DESHABILITADO.name(), errorMsg);
		}
		
		if ( empresaRestringidaService.esRestringido(user.getNombre()) ) {		
			String errorMsg = messageSource.getMessage(LoginEnumException.USUARIO_EMPRESA_RESTRINGIDA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(LoginEnumException.USUARIO_EMPRESA_RESTRINGIDA.name(), 
					String.format(errorMsg, user.getNombre())	);			
		}
		
		
		//TODO:  VALIDACION de CLAVE (habilitar/deshabilitar)
		if (!passwordEncryptor.iguales(login.clave, user.getClave())) {			
			String errorMsg = messageSource.getMessage(LoginEnumException.ERROR_CREDENCIAL_PWD.getMsgKey(), null, new Locale("es"));			
			throw new LoginException(LoginEnumException.ERROR_CREDENCIAL_PWD.name(), errorMsg);
		}
		log.debug("User {} authenticated", login.usuario);
		JWTokenBo result;
		
		if ( fetchUserHasTwoFactorAuthenticationEnabled.run(user.getId())) {
			//TODO: VER PORQUE NO ANDA!!!
			//result = generatePartiallyAuthenticationToken.run(user.getId(), user.getNombre());
			result = generateToken.generateTokens(user.getId(), user.getNombre());
		} else {
			result = generateToken.generateTokens(user.getId(), user.getNombre());
			usuarioInfoStorage.actualizarLoginDate(user.getNombre());
		}
		
		log.debug("Token generated");
		return result;
		//return  new JWTokenBo("", "");
	}

}
