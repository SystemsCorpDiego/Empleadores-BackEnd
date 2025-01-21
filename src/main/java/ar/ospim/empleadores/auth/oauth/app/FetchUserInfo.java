package ar.ospim.empleadores.auth.oauth.app;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.oauth.dominio.OAuthUserInfoBo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FetchUserInfo {

    public static final String BEARER = "Bearer ";
    private final OAuthUserInfoStorage oAuthUserInfoStorage;

    public Optional<OAuthUserInfoBo> run(String accessToken) {
        if (!accessToken.startsWith(BEARER))
            accessToken = BEARER + accessToken;
        Optional<OAuthUserInfoBo> result = oAuthUserInfoStorage.getUserInfo(accessToken);
        log.debug("Output -> {}", result);
        return result;
    }
}
