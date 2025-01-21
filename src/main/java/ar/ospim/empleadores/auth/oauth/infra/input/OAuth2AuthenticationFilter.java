package ar.ospim.empleadores.auth.oauth.infra.input;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ar.ospim.empleadores.auth.oauth.app.FetchUserInfo;
import ar.ospim.empleadores.auth.oauth.app.LoadUserAuthentication;
import ar.ospim.empleadores.auth.oauth.dominio.OAuthUserInfoBo;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFilter extends OncePerRequestFilter {

    @Value("${ws.oauth.enabled:false}")
    private boolean filterEnabled;

    private final FetchUserInfo fetchUserInfo;

    private final LoadUserAuthentication loadUserAuthentication;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (this.filterEnabled
                && securityContext.getAuthentication() == null) {
            String accessToken = httpServletRequest.getHeader("Authorization");
            if (accessToken != null) {
                Optional<OAuthUserInfoBo> opUserInfo = fetchUserInfo.run(accessToken);
                opUserInfo.flatMap(loadUserAuthentication::run)
                        .ifPresent(securityContext::setAuthentication);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
