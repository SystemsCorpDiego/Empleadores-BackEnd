package ar.ospim.empleadores.seguridad.infra.filtro;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import ar.ospim.empleadores.auth.jwt.dominio.TokenTipoEnum;
import ar.ospim.empleadores.auth.jwt.infra.input.rest.AuthenticationExternalService;
import ar.ospim.empleadores.auth.jwt.infra.input.rest.filtro.AuthenticationTokenFilter;
import ar.ospim.empleadores.auth.jwt.infra.input.rest.filtro.JWTFilter;
import ar.ospim.empleadores.auth.jwt.infra.output.token.TokenUtils;
import ar.ospim.empleadores.auth.usuario.app.RolAsignado;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.ERol;
import ar.ospim.empleadores.seguridad.infra.auth.SigecoGrantedAuthority;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TwoFactorAuthenticationFilter extends JWTFilter {

	protected TwoFactorAuthenticationFilter(
			@Value("${token.secret}") String secret,
			@Value("${token.header}") String tokenHeader,
			AuthenticationExternalService authenticationExternalService) {
		super(
				authenticationLoader(authenticationExternalService, secret),
				request -> AuthenticationTokenFilter.readFromCookie(request)
						.or(() -> Optional.ofNullable(request.getHeader(tokenHeader)))
						.or(() -> Optional.ofNullable(request.getHeader("Authorization")))
		);
	}

	public static Function<String, Optional<Authentication>> authenticationLoader(
			AuthenticationExternalService authenticationExternalService,
			String secret
	) {
		return (String token) -> TokenUtils.parseToken(token, secret, TokenTipoEnum.AUTENTICACION_PARCIAL)
				.flatMap(tokenData -> authenticationExternalService.getAppAuthentication(tokenData.usuario))
				.map(TwoFactorAuthenticationFilter::loadPermission);
	}

	private static Authentication loadPermission(Authentication authentication) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SigecoGrantedAuthority(new RolAsignado(ERol.AUTENTICACION_PARCIAL.getId(), ERol.AUTENTICACION_PARCIAL.getValue() )));
		return new UsernamePasswordAuthenticationToken(
				authentication.getPrincipal(),
				authentication.getCredentials(),
				authorities
		);
	}

	@Override
	public boolean shouldNotFilter(HttpServletRequest request) {
		boolean matchesPermission = new AntPathRequestMatcher("/account/permissions").matches(request);
		boolean matchesTwoFactorAuthentication = new AntPathRequestMatcher("/auth/login-dfa").matches(request);
		boolean result = !(matchesPermission || matchesTwoFactorAuthentication);
		log.debug("Should not filter request {} ?, result {}", request.getPathInfo(), result);
		return result;
	}

}
