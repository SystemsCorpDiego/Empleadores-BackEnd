package ar.ospim.empleadores.auth.usuario.app.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.ActualizarLoginDate;
import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;

@Service
public class ActualizarLoginDateImpl  implements ActualizarLoginDate {

    private final Logger logger;

    private final UsuarioStorage usuarioStorage;

    private final DateTimeProvider dateTimeProvider;

    public ActualizarLoginDateImpl(UsuarioStorage usuarioStorage,
                               DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.usuarioStorage = usuarioStorage;
    }

    @Override
    public void execute(String username) {
        logger.debug("Enable user -> {}", username);
        UsuarioBO user = usuarioStorage.getUsuario(username);
		user.setPrevioLogin(user.getPrevioLogin());
        user.setUltimoLogin(dateTimeProvider.nowDateTime());
        usuarioStorage.actualizar(user);
    }

}
