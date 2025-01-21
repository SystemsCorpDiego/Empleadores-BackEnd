package ar.ospim.empleadores.seguridad.infra.filtro;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ar.ospim.empleadores.auth.apikey.infra.input.service.ApiKeyExternalService;
import ar.ospim.empleadores.auth.usuario.app.UsuarioRolAsignacionService;
import ar.ospim.empleadores.comun.auth.usuario.SecurityContextUtils;
import ar.ospim.empleadores.comun.auth.usuario.SgxUserDetails;
import ar.ospim.empleadores.seguridad.infra.auth.SigecoGrantedAuthority;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PublicApiAuthenticationFilter  extends OncePerRequestFilter {

	private final String apiKeyHeader;

	private final ApiKeyExternalService apiKeyExternalService;

	private final UsuarioRolAsignacionService userAssignmentService;

	protected PublicApiAuthenticationFilter(
			@Value("${api-key.header}") String apiKeyHeader,
			ApiKeyExternalService apiKeyExternalService,
			UsuarioRolAsignacionService userAssignmentService
	) {
		this.apiKeyHeader = apiKeyHeader;
		this.apiKeyExternalService = apiKeyExternalService;
		this.userAssignmentService = userAssignmentService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		Optional.ofNullable(SecurityContextUtils.getAuthentication())
				.ifPresentOrElse(
						auth -> SecurityContextHolder.getContext().setAuthentication(auth),
						() -> extractApiKey(request)
								.flatMap(apiKeyExternalService::login)
								.map(userInfo -> new UsernamePasswordAuthenticationToken(new SgxUserDetails(userInfo.getId()), "", getAuthorities(userInfo.getId())))
								.ifPresent(opA -> SecurityContextHolder.getContext().setAuthentication(opA)));
		log.debug("Request {}", request);
		chain.doFilter(request, response);
		log.debug("Response {}", response);
	}


	private Collection<GrantedAuthority> getAuthorities(Integer id) {
		log.debug("Get authorities from user {}", id);
		return userAssignmentService.getRolAsignado(id).stream()
				.map(SigecoGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	protected Optional<String> extractApiKey(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(apiKeyHeader));
	}

	@Override
	public boolean shouldNotFilter(HttpServletRequest request) {
		var matcher = new AntPathRequestMatcher("/public-api/**");
		boolean result = !matcher.matches(request);
		log.debug("Should not filter request {} ?, result {}", request.getPathInfo(), result);
		return result;
	}


}
