package ar.ospim.empleadores.auth.jwt.infra.input.rest.filtro;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import ar.ospim.empleadores.auth.jwt.dominio.TokenTipoEnum;
import ar.ospim.empleadores.auth.jwt.infra.input.rest.AuthenticationExternalService;
import ar.ospim.empleadores.auth.jwt.infra.output.token.TokenUtils;

@Component
public class AuthenticationTokenFilter  extends JWTFilter {

	public AuthenticationTokenFilter(
			@Value("${token.header}") String tokenHeader,
			@Value("${token.secret}") String secret,
			AuthenticationExternalService authenticationExternalService
	) {
		super(
				authenticationLoader(authenticationExternalService, secret),
				request -> readFromCookie(request)
						.or(() -> Optional.ofNullable(request.getHeader(tokenHeader)))
						.or(() -> Optional.ofNullable(request.getHeader("Authorization")))
		);
	}

	public static Function<String, Optional<Authentication>> authenticationLoader(
			AuthenticationExternalService authenticationExternalService,
			String secret
	) {
		return (String token) -> TokenUtils.parseToken(token, secret, TokenTipoEnum.NORMAL)
				.flatMap(tokenData -> authenticationExternalService.getAppAuthentication(tokenData.usuario));
	}

	public static Optional<String> readFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return Optional.empty();
		}
		return Arrays.stream(cookies)
				.filter(c -> "token".equals(c.getName()))
				.map(Cookie::getValue)
				.findFirst();

	}
}
