package ar.ospim.empleadores.auth.oauth.app.port;

import java.util.Optional;

import ar.ospim.empleadores.auth.jwt.dominio.JWTokenBo;

public interface OAuthTokenStorage {

    Optional<JWTokenBo> refreshToken(String refreshToken);

}
