package ar.ospim.empleadores.seguridad.infra.filtro;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ar.ospim.empleadores.auth.usuario.app.UsuarioRolAsignacionService;
import ar.ospim.empleadores.comun.auth.usuario.SecurityContextUtils;
import ar.ospim.empleadores.comun.auth.usuario.SgxUserDetails;
import ar.ospim.empleadores.seguridad.infra.auth.SigecoGrantedAuthority;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthorizationFilter extends OncePerRequestFilter implements Filter {

	private final UsuarioRolAsignacionService userAssignmentService;

	public AuthorizationFilter(UsuarioRolAsignacionService userAssignmentService) {
		this.userAssignmentService = userAssignmentService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		Optional.ofNullable(SecurityContextUtils.getAuthentication())
				.map(auth -> new UsernamePasswordAuthenticationToken(
						auth.getPrincipal(),
						auth.getCredentials(),
						getAuthorities(((SgxUserDetails)auth.getPrincipal()).getUserId())))
				.ifPresent(opA -> SecurityContextHolder.getContext().setAuthentication(opA));

		log.debug("Request {}", request);
		chain.doFilter(request, response);
		log.debug("Response {}", response);
	}

	private Collection<GrantedAuthority> getAuthorities(Integer userId) {
		//Los permisos del usuario se obtienen de usuario_rol
		return userAssignmentService.getRolAsignado(userId)
				.stream()
				.map(SigecoGrantedAuthority::new)
				.collect(Collectors.toList());
	}

}
