package ar.ospim.empleadores.comun.restclient.servicios;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ar.ospim.empleadores.comun.restclient.config.WSConfig;
import ar.ospim.empleadores.comun.restclient.config.resttemplate.RestTemplateSSL;
import ar.ospim.empleadores.comun.restclient.servicios.dominio.LoginResponse;
import ar.ospim.empleadores.comun.restclient.servicios.dominio.WSResponseException;

public abstract class AuthService <R extends LoginResponse> extends RestClient {

	protected final String relUrl;

	public AuthService(String relUrl, RestTemplateSSL restTemplateSSL, WSConfig tawsConfig) {
		super(restTemplateSSL, tawsConfig);
		this.relUrl = relUrl;
	}

	protected abstract ResponseEntity<R> callLogin() throws WSResponseException;

	public R login() throws WSResponseException {
		return getValidResponse(callLogin());
	}

	private R getValidResponse(ResponseEntity<R> result) throws WSResponseException {
		assertValidStatusCode(result);
		R loginResponse = result.getBody();
		assertValidResponse(loginResponse);
		return loginResponse;
	}

	protected void assertValidResponse(R loginResponse) throws WSResponseException {
		if (loginResponse == null) {
			throw new WSResponseException("WS Response is null");
		}
		if (loginResponse.getToken() == null) {
			throw new WSResponseException("WS Response token is null");
		}
	}

	protected static void assertValidStatusCode(ResponseEntity<?> responseEntity) throws WSResponseException {
		if (responseEntity.getStatusCode() != HttpStatus.OK) {
			throw new WSResponseException(
					String.format("WS Response status code is %s", responseEntity.getStatusCode()));
		}
	}
}
