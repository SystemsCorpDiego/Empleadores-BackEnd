package ar.ospim.empleadores.auth.usuario.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.dominio.usuario.modelo.OAuthUsuarioBo;
import ar.ospim.empleadores.auth.usuario.dominio.usuario.servicio.OAuthUsuarioManagementStorage;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateClaveUsuario {

    private final UpdateClave updatePassword;
    private final OAuthUsuarioManagementStorage oAuthUserManagementStorage;

    @Value("${ws.oauth.enabled:false}")
    private boolean oAuthServiceEnabled;

    public void run(UsuarioBO user, String password) {
        log.debug("Input parameter -> user {}", user);

        if (!oAuthServiceEnabled) {
            updatePassword.execute(user.getDescripcion(), password);
        }
        else {
            oAuthUserManagementStorage.actualizarUsuario(user.getDescripcion(), new OAuthUsuarioBo(null, password, null, null, null));        }
    }
}
