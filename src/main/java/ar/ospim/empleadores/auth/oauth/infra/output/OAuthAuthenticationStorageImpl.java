package ar.ospim.empleadores.auth.oauth.infra.output;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.jwt.infra.input.rest.AuthenticationExternalService;
import ar.ospim.empleadores.auth.oauth.app.port.OAuthAuthenticationStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthAuthenticationStorageImpl  implements OAuthAuthenticationStorage {

    private final AuthenticationExternalService authenticationExternalService;

    @Override
    public Optional<Authentication> getAppAuthentication(String username) {
        log.debug("Input parameter -> username {}", username);
        Optional<Authentication> result = authenticationExternalService.getAppAuthentication(username);
        log.debug("Output -> {}", result);
        return result;
    }
}
