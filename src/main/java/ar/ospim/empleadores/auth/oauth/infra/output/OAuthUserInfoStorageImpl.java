package ar.ospim.empleadores.auth.oauth.infra.output;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.oauth.app.OAuthUserInfoStorage;
import ar.ospim.empleadores.auth.oauth.dominio.OAuthUserInfoBo;
import ar.ospim.empleadores.auth.oauth.infra.output.config.OAuthWSConfig;
import ar.ospim.empleadores.comun.restclient.config.HttpClientConfiguration;
import ar.ospim.empleadores.comun.restclient.config.resttemplate.RestTemplateSSL;
import ar.ospim.empleadores.comun.restclient.servicios.RestClient;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OAuthUserInfoStorageImpl extends RestClient implements OAuthUserInfoStorage {

    private final OAuthWSConfig oAuthWSConfig;

    private final OAuthUserMapper oAuthUserMapper;

    public OAuthUserInfoStorageImpl(
			HttpClientConfiguration configuration,
			OAuthWSConfig oAuthWSConfig,
			OAuthUserMapper oAuthUserMapper
	) throws Exception {
        super(
				new RestTemplateSSL(configuration),
				oAuthWSConfig
		);
        this.oAuthWSConfig = oAuthWSConfig;
        this.oAuthUserMapper = oAuthUserMapper;
    }

    @Override
    public Optional<OAuthUserInfoBo> getUserInfo(String accessToken) {

        ResponseEntity<OAuthUserInfoDto> responseEntity = getOAuthUserInfoResponse(accessToken);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Output -> No user info obtained");
            return Optional.empty();
        }
        OAuthUserInfoDto responseDto = responseEntity.getBody();
        Optional<OAuthUserInfoBo> opOAuthUserInfoBo = Optional.of(oAuthUserMapper.fromOAuthUserInfoDto(responseDto));
        log.debug("Output -> {}", opOAuthUserInfoBo);
        return opOAuthUserInfoBo;
    }

    private ResponseEntity<OAuthUserInfoDto> getOAuthUserInfoResponse(String accessToken) {
        String url = oAuthWSConfig.getUserInfo();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        return exchangeGet(url, headers, OAuthUserInfoDto.class);
    }

}
