package ar.ospim.empleadores.auth.usuario.app.impl;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.DeshabilitarUsuario;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeshabilitarUsuarioImpl  implements DeshabilitarUsuario {

    private final UsuarioStorage usuarioStorage;

    public DeshabilitarUsuarioImpl(UsuarioStorage usuarioStorage) {
        this.usuarioStorage = usuarioStorage;
    }

    @Override
    public void run(String username) {
        log.debug("Disable user -> {}", username);
        UsuarioBO user = usuarioStorage.getUsuario(username);
        user.setHabilitado(false);
        usuarioStorage.actualizar(user);
    }

    @Override
    public void run(Integer id) {
        log.debug("Disable user -> {}", id);
        UsuarioBO user = usuarioStorage.getUsuario(id);
        user.setHabilitado(false);
        usuarioStorage.actualizar(user);
    }
}
