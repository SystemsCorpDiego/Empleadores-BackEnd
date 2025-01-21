package ar.ospim.empleadores.comun.restclient.config.resttemplate;

import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import ar.ospim.empleadores.comun.restclient.config.HttpClientConfiguration;
import ar.ospim.empleadores.comun.restclient.config.RestUtils;
import ar.ospim.empleadores.comun.restclient.config.interceptors.LoggingRequestInterceptor;
import ar.ospim.empleadores.comun.restclient.config.interceptors.MonitoringRequestInterceptor;

public class RestTemplateSSL  extends RestTemplate {

	public RestTemplateSSL(HttpClientConfiguration configuration) throws Exception {
		super(new BufferingClientHttpRequestFactory(getClientHttpRequestFactory(
				configuration.getTimeout(),
				configuration.isTrustInvalidCertificate(),
				configuration.getProxy()
		)));
		this.getInterceptors().add(new LoggingRequestInterceptor());
		this.getInterceptors().add(new MonitoringRequestInterceptor());
		this.setErrorHandler(new RestTemplateExceptionHandler());
	}

	private static HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory(
			Integer timeout,
			boolean trustInvalidCertificate,
			String httpProxy
	) throws Exception {

		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setHttpClient(RestUtils.httpClient(
				trustInvalidCertificate,
				httpProxy
		));
		clientHttpRequestFactory.setConnectTimeout(timeout);
		clientHttpRequestFactory.setReadTimeout(timeout);
		clientHttpRequestFactory.setConnectionRequestTimeout(timeout);
		return clientHttpRequestFactory;
	}

}
