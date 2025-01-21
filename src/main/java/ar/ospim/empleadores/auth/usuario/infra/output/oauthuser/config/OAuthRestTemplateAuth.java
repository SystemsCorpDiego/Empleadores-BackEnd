package ar.ospim.empleadores.auth.usuario.infra.output.oauthuser.config;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.restclient.config.HttpClientConfiguration;
import ar.ospim.empleadores.comun.restclient.config.resttemplate.RestTemplateAuth;

@Service
public class OAuthRestTemplateAuth extends RestTemplateAuth<OAuthAuthInterceptor> {

	public OAuthRestTemplateAuth(
			OAuthAuthInterceptor authInterceptor,
			HttpClientConfiguration configuration
	) throws Exception {
		super(authInterceptor, configuration);
	}


}
