package ar.ospim.empleadores.auth.usuario.app.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.CrearTokenClaveReset;
import ar.ospim.empleadores.nuevo.infra.out.store.ClaveResetTokenStorage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CrearTokenClaveResetImpl  implements CrearTokenClaveReset {

    private final ClaveResetTokenStorage claveResetTokenStorage;

    public CrearTokenClaveResetImpl(ClaveResetTokenStorage claveResetTokenStorage) {
        this.claveResetTokenStorage = claveResetTokenStorage;
    }

    @Override
    public String execute(Integer userId) {
        log.debug("Input parameter -> {}", userId);
        return claveResetTokenStorage.crearToken(userId).getToken();
    }
}
