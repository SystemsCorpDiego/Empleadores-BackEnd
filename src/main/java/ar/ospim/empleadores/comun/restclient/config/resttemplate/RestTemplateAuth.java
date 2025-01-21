package ar.ospim.empleadores.comun.restclient.config.resttemplate;

import org.springframework.http.client.ClientHttpRequestInterceptor;

import ar.ospim.empleadores.comun.restclient.config.HttpClientConfiguration;

public class RestTemplateAuth<I extends ClientHttpRequestInterceptor> extends RestTemplateSSL {

	public RestTemplateAuth(I authInterceptor, HttpClientConfiguration configuration) throws Exception {
		super(configuration);
		this.getInterceptors().add(authInterceptor);
	}
		
}
