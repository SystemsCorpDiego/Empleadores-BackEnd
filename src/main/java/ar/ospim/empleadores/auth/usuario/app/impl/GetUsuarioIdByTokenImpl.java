package ar.ospim.empleadores.auth.usuario.app.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.GetUsuarioIdByToken;
import ar.ospim.empleadores.nuevo.infra.out.store.ClaveResetTokenStorage;

@Service
public class GetUsuarioIdByTokenImpl  implements GetUsuarioIdByToken {

    private final Logger logger;

    private final ClaveResetTokenStorage claveResetTokenStorage;

    public GetUsuarioIdByTokenImpl(ClaveResetTokenStorage claveResetTokenStorage) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.claveResetTokenStorage = claveResetTokenStorage;
    }


    @Override
    public Integer execute(String token) {
        Integer result = claveResetTokenStorage.get(token).getUsuarioId();
        logger.debug("Output -> {}", result);
        return result;
    }
}
