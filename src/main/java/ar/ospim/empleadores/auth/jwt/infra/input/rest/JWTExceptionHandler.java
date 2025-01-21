package ar.ospim.empleadores.auth.jwt.infra.input.rest;

import java.util.Locale;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ar.ospim.empleadores.auth.jwt.app.BadRefreshTokenException;
import ar.ospim.empleadores.auth.jwt.app.cookie.CookieService;
import ar.ospim.empleadores.auth.jwt.app.login.LoginException;
import ar.ospim.empleadores.comun.exception.ApiErrorMessageDto;
import ar.ospim.empleadores.comun.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages = "ar.ospim.empleadores.auth.jwt")
public class JWTExceptionHandler {

	private final CookieService cookieService;

	//TODO: ver porque no anda. Lo tuve que repetir en CustomExceptionHandler.-
	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	@ExceptionHandler({ LoginException.class })
	protected ApiErrorMessageDto handleBadLoginException(LoginException ex) {
		log.debug("LoginException exception -> {}", ex.getMessage());
		BusinessException exB = new BusinessException( null, ex.getMessage() );
		
		return new ApiErrorMessageDto(exB.getErrorType(), exB.getTicketError(), null, ex.getMessage());
		//return new ApiErrorMessageDto(null, ex.getMessage());
	}


	@ExceptionHandler({ BadRefreshTokenException.class })
	protected ResponseEntity<ApiErrorMessageDto> handleBadRefreshTokenException(BadRefreshTokenException ex, Locale locale) {
		log.debug("BadRefreshTokenException exception -> {}", ex.getMessage());
		return cookieService.deleteTokensResponse(HttpStatus.PRECONDITION_FAILED)
				.body(new ApiErrorMessageDto(null, ex.getMessage()));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ BadCredentialsException.class })
	public ApiErrorMessageDto invalidCredentials(BadCredentialsException ex) {
		log.warn(ex.getMessage(), ex);
		return new ApiErrorMessageDto(ex.getMessage(), "Nombre de usuario o clave inválidos");
	}



}

