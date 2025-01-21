package ar.ospim.empleadores.auth.usuario.infra.output.oauthuser.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import ar.ospim.empleadores.comun.restclient.config.TokenHolder;
import ar.ospim.empleadores.comun.restclient.servicios.AuthService;
import ar.ospim.empleadores.comun.restclient.servicios.dominio.LoginResponse;
import ar.ospim.empleadores.comun.restclient.servicios.dominio.WSResponseException;

public abstract class AuthInterceptor<R extends LoginResponse, S extends AuthService<R>> implements ClientHttpRequestInterceptor {
	
	protected final TokenHolder token;
	
	private S authService;
	
	public AuthInterceptor(S authService, TokenHolder tokenHolder) {
		this.authService = authService;
		this.token = tokenHolder;
	}
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
		throws IOException {
		if (!token.isValid()) {
			callLogin();
		}
		HttpHeaders headers = request.getHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		addAuthHeaders(headers);
		ClientHttpResponse response = execution.execute(request, body);
		if (loginRequired(response)) {
			callLogin();
			addAuthHeaders(headers);
			response = execution.execute(request, body);
		}
		return response;
	}
	
	protected abstract void addAuthHeaders(HttpHeaders headers);
	
	private boolean loginRequired(ClientHttpResponse response) throws IOException {
		return response.getStatusCode() == HttpStatus.FORBIDDEN
				|| response.getStatusCode() == HttpStatus.UNAUTHORIZED;
	}
	
	private void callLogin() throws IOException {
		try {
			token.set(authService.login().getToken());
		} catch (WSResponseException e) {
			throw new IOException(e.getMessage(), e);
		}
	}

}
