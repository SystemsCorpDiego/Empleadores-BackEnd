package ar.ospim.empleadores.auth.oauth.app;

import java.util.Optional;

import ar.ospim.empleadores.auth.oauth.dominio.OAuthUserInfoBo;

public interface OAuthUserInfoStorage {

    Optional<OAuthUserInfoBo> getUserInfo(String accessToken);

}
