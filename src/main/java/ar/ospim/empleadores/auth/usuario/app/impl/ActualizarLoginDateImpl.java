package ar.ospim.empleadores.auth.usuario.app.impl;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.ActualizarLoginDate;
import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActualizarLoginDateImpl  implements ActualizarLoginDate {

    private final UsuarioStorage usuarioStorage;

    private final DateTimeProvider dateTimeProvider;

    public ActualizarLoginDateImpl(UsuarioStorage usuarioStorage,
                               DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
        this.usuarioStorage = usuarioStorage;
    }

    @Override
    public void execute(String username) {
        log.debug("Enable user -> {}", username);
        UsuarioBO user = usuarioStorage.getUsuario(username);
		user.setPrevioLogin(user.getPrevioLogin());
        user.setUltimoLogin(dateTimeProvider.nowDateTime());
        usuarioStorage.actualizar(user);
    }

}
