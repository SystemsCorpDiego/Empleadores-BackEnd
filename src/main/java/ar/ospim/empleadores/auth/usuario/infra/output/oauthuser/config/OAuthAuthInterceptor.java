package ar.ospim.empleadores.auth.usuario.infra.output.oauthuser.config;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import ar.ospim.empleadores.auth.oauth.infra.output.config.OAuthWSConfig;
import ar.ospim.empleadores.comun.restclient.config.TokenHolder;

@Component
public class OAuthAuthInterceptor extends AuthInterceptor<OAuthLoginResponse, OAuthAuthService> {

	public OAuthAuthInterceptor(OAuthAuthService authService, OAuthWSConfig oAuthWSConfig) {
		super(authService, new TokenHolder(oAuthWSConfig.getTokenExpiration()));
	}

	@Override
	protected void addAuthHeaders(HttpHeaders headers) {
		headers.setBearerAuth(token.get());
	}


}
