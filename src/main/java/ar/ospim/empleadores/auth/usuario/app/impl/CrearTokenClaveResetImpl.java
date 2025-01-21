package ar.ospim.empleadores.auth.usuario.app.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.CrearTokenClaveReset;
import ar.ospim.empleadores.nuevo.infra.out.store.ClaveResetTokenStorage;

@Service
public class CrearTokenClaveResetImpl  implements CrearTokenClaveReset {

    private final Logger logger;

    private final ClaveResetTokenStorage claveResetTokenStorage;

    public CrearTokenClaveResetImpl(ClaveResetTokenStorage claveResetTokenStorage) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.claveResetTokenStorage = claveResetTokenStorage;
    }

    @Override
    public String execute(Integer userId) {
        logger.debug("Input parameter -> {}", userId);
        return claveResetTokenStorage.crearToken(userId).getToken();
    }
}
