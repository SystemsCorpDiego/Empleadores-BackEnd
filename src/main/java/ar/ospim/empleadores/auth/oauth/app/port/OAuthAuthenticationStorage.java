package ar.ospim.empleadores.auth.oauth.app.port;

import java.util.Optional;

import org.springframework.security.core.Authentication;

public interface OAuthAuthenticationStorage {

    Optional<Authentication> getAppAuthentication(String username);

}
