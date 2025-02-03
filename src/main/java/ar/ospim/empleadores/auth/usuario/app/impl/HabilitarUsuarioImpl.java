package ar.ospim.empleadores.auth.usuario.app.impl;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.HabilitarUsuario;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HabilitarUsuarioImpl implements HabilitarUsuario {

    private final UsuarioStorage usuarioStorage;

    public HabilitarUsuarioImpl(UsuarioStorage usuarioStorage) {
        this.usuarioStorage = usuarioStorage;
    }

    @Override
    public void run(String usuario) {
        log.debug("Enable user -> {}", usuario);
        UsuarioBO usuarioBo = usuarioStorage.getUsuario(usuario);
        usuarioBo.setHabilitado(true);
        usuarioStorage.actualizar(usuarioBo);
    }

    @Override
    public void run(Integer id) {
        log.debug("Enable user -> {}", id);
        UsuarioBO usuarioBo = usuarioStorage.getUsuario(id);
        usuarioBo.setHabilitado(true);
        usuarioStorage.actualizar(usuarioBo);
    }

}
