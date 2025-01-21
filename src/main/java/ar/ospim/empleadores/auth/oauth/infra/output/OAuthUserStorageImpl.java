package ar.ospim.empleadores.auth.oauth.infra.output;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.oauth.app.port.OAuthUserStorage;
import ar.ospim.empleadores.auth.usuario.infra.input.servicio.UsuarioExternalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthUserStorageImpl implements OAuthUserStorage {

    private final UsuarioExternalService usuarioExternalService;

    public void registerUser(String username, String email, String password) {
        log.debug("Input parameters -> username {}, email {}", username, email);
        usuarioExternalService.registrarUsuario(username, email, password, null);
    }

    public void enableUser(String username) {
        log.debug("Input parameters -> username {}", username);
        usuarioExternalService.habilitarUsuario(username);
    }
}
