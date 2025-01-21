package ar.ospim.empleadores.auth.usuario.infra.output.oauthuser.config;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.oauth.infra.output.config.OAuthWSConfig;
import ar.ospim.empleadores.comun.restclient.config.HttpClientConfiguration;
import ar.ospim.empleadores.comun.restclient.config.resttemplate.RestTemplateSSL;
import ar.ospim.empleadores.comun.restclient.servicios.AuthService;
import ar.ospim.empleadores.comun.restclient.servicios.dominio.WSResponseException;

@Service
public class OAuthAuthService extends AuthService<OAuthLoginResponse> {

	private OAuthWSConfig oAuthWSConfig;

	public OAuthAuthService(
			HttpClientConfiguration configuration,
			OAuthWSConfig wsConfig
	) throws Exception {
		super(wsConfig.getFetchAccessToken(), getRestTemplateSSL(configuration), wsConfig);
		oAuthWSConfig = wsConfig;
	}

	private static RestTemplateSSL getRestTemplateSSL(
			HttpClientConfiguration configuration
	) throws Exception {
		var restTemplate = new RestTemplateSSL(
				configuration
		);
		restTemplate.getInterceptors().add(0, new OAuthLoginInterceptor()); // adds the interceptor in the first position
		return restTemplate;
	}

	@Override
	protected ResponseEntity<OAuthLoginResponse> callLogin() throws WSResponseException {
		ResponseEntity<OAuthLoginResponse> result = null;
		String loginPayload = buildLoginPayload();
		try {
			result = exchangePost(relUrl, loginPayload, OAuthLoginResponse.class);
		} catch (Exception e) {
			throw new WSResponseException("Error en login de administrador de usuarios en servidor de OAuth -> " + e.getMessage() ) ;
		}
		return result;
	}

	private String buildLoginPayload() {
		return "username=" + oAuthWSConfig.getUserAdminUsername() +
				"&password=" + oAuthWSConfig.getUserAdminPassword() +
				"&client_id=" + oAuthWSConfig.getClientId() +
				"&grant_type=password";
	}


}
