package ar.ospim.empleadores.auth.oauth.infra.output;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.jwt.dominio.JWTokenBo;
import ar.ospim.empleadores.auth.oauth.app.port.OAuthTokenStorage;
import ar.ospim.empleadores.auth.oauth.infra.output.config.OAuthWSConfig;
import ar.ospim.empleadores.auth.oauth.infra.output.dto.OAuthRefrescoTokenResponse;
import ar.ospim.empleadores.auth.usuario.infra.output.oauthuser.config.OAuthLoginInterceptor;
import ar.ospim.empleadores.comun.restclient.config.HttpClientConfiguration;
import ar.ospim.empleadores.comun.restclient.config.resttemplate.RestTemplateSSL;
import ar.ospim.empleadores.comun.restclient.servicios.RestClient;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OAuthTokenStorageImpl extends RestClient implements OAuthTokenStorage {

    private final OAuthWSConfig oAuthWSConfig;

    public OAuthTokenStorageImpl(
			HttpClientConfiguration configuration,
			OAuthWSConfig oAuthWSConfig
	) throws Exception {
        super(getRestTemplateSSL(configuration), oAuthWSConfig);
        this.oAuthWSConfig = oAuthWSConfig;
    }

    private static RestTemplateSSL getRestTemplateSSL(
			HttpClientConfiguration configuration
	) throws Exception {
        var restTemplate = new RestTemplateSSL(configuration);
        restTemplate.getInterceptors().add(0, new OAuthLoginInterceptor()); // adds the interceptor in the first position
        return restTemplate;
    }

    @Override
    public Optional<JWTokenBo> refreshToken(String refreshToken) {
        log.debug("Input parameter -> refreshToken {}", refreshToken);
        String url = oAuthWSConfig.getFetchAccessToken();
        try {
            ResponseEntity<OAuthRefrescoTokenResponse> response = exchangePost(url, mapToRefreshTokenPayload(refreshToken), OAuthRefrescoTokenResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.debug("Token refresh successful in OAuth Server");
                OAuthRefrescoTokenResponse responseBody = response.getBody();
                return Optional.of(new JWTokenBo(responseBody.getAccessToken(),responseBody.getRefreshToken()));
            }
        } catch (Exception e) {
            log.debug("Error refreshing token in OAuth Server");
        }
        return Optional.empty();
    }

    private String mapToRefreshTokenPayload(String refreshToken) {
        return "refresh_token=" + refreshToken +
                "&client_id=" + oAuthWSConfig.getClientId() +
                "&grant_type=refresh_token";
    }

}