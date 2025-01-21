package ar.ospim.empleadores.auth.oauth.app;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.oauth.app.port.OAuthAuthenticationStorage;
import ar.ospim.empleadores.auth.oauth.app.port.OAuthUserStorage;
import ar.ospim.empleadores.auth.oauth.dominio.OAuthUserInfoBo;
import ar.ospim.empleadores.comun.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoadUserAuthentication {

    private final OAuthAuthenticationStorage oAuthAuthenticationStorage;
    private final OAuthUserStorage oAuthUserStorage;

    public Optional<Authentication> run(OAuthUserInfoBo userInfo) {
        log.debug("Input parameter -> userInfo {}", userInfo);
        Optional<Authentication> authentication;

        try {
            authentication = oAuthAuthenticationStorage.getAppAuthentication(userInfo.getUsername());
        } catch (BusinessException e) { // this means that the user has not been created yet
            oAuthUserStorage.registerUser(userInfo.getUsername(), userInfo.getEmail(), null);
            oAuthUserStorage.enableUser(userInfo.getUsername());
            authentication = oAuthAuthenticationStorage.getAppAuthentication(userInfo.getUsername());
        }
        return authentication;
    }

}
