package ar.ospim.empleadores.auth.usuario.app.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.DeshabilitarUsuario;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;

@Service
public class DeshabilitarUsuarioImpl  implements DeshabilitarUsuario {

    private final Logger logger;

    private final UsuarioStorage usuarioStorage;

    public DeshabilitarUsuarioImpl(UsuarioStorage usuarioStorage) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.usuarioStorage = usuarioStorage;
    }

    @Override
    public void run(String username) {
        logger.debug("Disable user -> {}", username);
        UsuarioBO user = usuarioStorage.getUsuario(username);
        user.setHabilitado(false);
        usuarioStorage.actualizar(user);
    }

    @Override
    public void run(Integer id) {
        logger.debug("Disable user -> {}", id);
        UsuarioBO user = usuarioStorage.getUsuario(id);
        user.setHabilitado(false);
        usuarioStorage.actualizar(user);
    }
}
