package ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.auth.jwt.app.BadRefreshTokenException;
import ar.ospim.empleadores.auth.jwt.app.cookie.CookieService;
import ar.ospim.empleadores.auth.jwt.app.login.Login;
import ar.ospim.empleadores.auth.jwt.app.login.LoginException;
import ar.ospim.empleadores.auth.jwt.app.logindfa.LoginDFA;
import ar.ospim.empleadores.auth.jwt.app.refrescartoken.RefrescarToken;
import ar.ospim.empleadores.auth.jwt.dominio.JWTokenBo;
import ar.ospim.empleadores.auth.jwt.dominio.LoginBo;
import ar.ospim.empleadores.auth.usuario.app.TokenGestionUsuario;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt.dto.DFALoginDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt.dto.JWTokenDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt.dto.LoginDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt.dto.RefreshTokenDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
//@Tag(name = "Authorization", description = "Authorization")
public class AutenticacionControllerU {
	
	private final Login login;
	//private final ICaptchaService captchaService;
	private final CookieService cookieService;	
	private final RefrescarToken refreshToken;
	private final LoginDFA loginTwoFactorAuthentication; 

	@PostMapping("/login")
	public ResponseEntity<JWTokenDto>  login(
			@Valid @RequestBody LoginDto loginDto,
			//@RequestHeader("Origin") String frontUrl,
			@RequestHeader(value = "recaptcha", required = false) String recaptcha
			) throws LoginException {
		
		//if (captchaService.isRecaptchaEnable()) {
			//captchaService.validRecaptcha(frontUrl, recaptcha);
		//}
		
		log.debug("Login attempt for user {}", loginDto.usuario);
		JWTokenBo resultToken = login.execute(new LoginBo(loginDto.usuario, loginDto.clave));
		log.debug("Token generated for user {}", loginDto.usuario);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookieService.tokenCookieHeader(resultToken.token))
				.header(HttpHeaders.SET_COOKIE, cookieService.refreshTokenCookieHeader(resultToken.tokenRefresco))
				.body(new JWTokenDto(resultToken.token, resultToken.tokenRefresco));
	}

	@PostMapping(value = "/login/token/refresh")
	public ResponseEntity<JWTokenDto> refreshToken(
			@CookieValue(name = "tokenRefresco", defaultValue =  "") String tokenRefrescoCookieValue,
			@RequestBody(required=false) RefreshTokenDto refreshTokenDto
			) throws BadRefreshTokenException {
		log.debug("Refreshing token");

		var tokenRefrescoValue = refreshTokenDto != null && !ObjectUtils.isEmpty(refreshTokenDto.tokenRefresco) ?
				refreshTokenDto.tokenRefresco : tokenRefrescoCookieValue;
		JWTokenBo resultToken = refreshToken.execute(tokenRefrescoValue);
		log.debug("Token refreshed");
		
		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookieService.tokenCookieHeader(resultToken.token))
				.header(HttpHeaders.SET_COOKIE, cookieService.refreshTokenCookieHeader(resultToken.tokenRefresco))
				.body(new JWTokenDto(resultToken.token, resultToken.tokenRefresco));
	}
	
	@PostMapping("/login-dfa")
	@PreAuthorize("hasAnyAuthority('PARTIALLY_AUTHENTICATED')")
	public ResponseEntity<JWTokenDto> completeLoginWith2FA(@RequestBody DFALoginDto loginDto) throws LoginException {
		JWTokenBo resultToken = loginTwoFactorAuthentication.execute(loginDto.getCodigo());
		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookieService.tokenCookieHeader(resultToken.token))
				.header(HttpHeaders.SET_COOKIE, cookieService.refreshTokenCookieHeader(resultToken.tokenRefresco))
				.body(new JWTokenDto(resultToken.token, resultToken.tokenRefresco));
	}

	@PostMapping("/login/usuario/recupero-clave/{mail}")
	public ResponseEntity<Void> recuperarClave(@PathParam("mail") String mail) throws LoginException {
		
		
		return ResponseEntity.ok(null);
	}
	
}
