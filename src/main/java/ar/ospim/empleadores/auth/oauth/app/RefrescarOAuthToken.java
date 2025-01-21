package ar.ospim.empleadores.auth.oauth.app;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.jwt.dominio.JWTokenBo;
import ar.ospim.empleadores.auth.oauth.app.port.OAuthTokenStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefrescarOAuthToken {

    private final OAuthTokenStorage oAuthTokenStorage;

    public Optional<JWTokenBo> run(String refreshToken) {
        log.debug("Input parameter -> refreshToken {}", refreshToken);
        Optional<JWTokenBo> result = oAuthTokenStorage.refreshToken(refreshToken);
        log.debug("Output -> {}", result);
        return result;
    }

}
