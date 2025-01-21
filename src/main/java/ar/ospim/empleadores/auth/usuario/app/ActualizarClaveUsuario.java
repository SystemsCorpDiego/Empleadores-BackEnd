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
public class ActualizarClaveUsuario {

    private final ActualizarClave actualizarClave;
    private final OAuthUsuarioManagementStorage oAuthUsuarioManagementStorage;

    @Value("${ws.oauth.enabled:false}")
    private boolean oAuthServiceEnabled;

    public void run(UsuarioBO usuario, String password) {
        log.debug("Input parameter -> user {}", usuario);

        if (!oAuthServiceEnabled) {
        	actualizarClave.execute(usuario.getDescripcion(), password);
        }
        else {
        	oAuthUsuarioManagementStorage.actualizarUsuario(usuario.getDescripcion(), new OAuthUsuarioBo(null, password, null, null, null));
        }
    }
}
