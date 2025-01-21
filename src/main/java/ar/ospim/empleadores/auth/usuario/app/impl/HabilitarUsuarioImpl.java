package ar.ospim.empleadores.auth.usuario.app.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.HabilitarUsuario;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;

@Service
public class HabilitarUsuarioImpl implements HabilitarUsuario {

    private final Logger logger;

    private final UsuarioStorage usuarioStorage;

    public HabilitarUsuarioImpl(UsuarioStorage usuarioStorage) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.usuarioStorage = usuarioStorage;
    }

    @Override
    public void run(String usuario) {
        logger.debug("Enable user -> {}", usuario);
        UsuarioBO usuarioBo = usuarioStorage.getUsuario(usuario);
        usuarioBo.setHabilitado(true);
        usuarioStorage.actualizar(usuarioBo);
    }

    @Override
    public void run(Integer id) {
        logger.debug("Enable user -> {}", id);
        UsuarioBO usuarioBo = usuarioStorage.getUsuario(id);
        usuarioBo.setHabilitado(true);
        usuarioStorage.actualizar(usuarioBo);
    }

}
