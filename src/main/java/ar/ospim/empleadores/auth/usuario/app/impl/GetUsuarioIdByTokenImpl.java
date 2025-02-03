package ar.ospim.empleadores.auth.usuario.app.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.GetUsuarioIdByToken;
import ar.ospim.empleadores.nuevo.infra.out.store.ClaveResetTokenStorage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GetUsuarioIdByTokenImpl  implements GetUsuarioIdByToken {

    private final ClaveResetTokenStorage claveResetTokenStorage;

    public GetUsuarioIdByTokenImpl(ClaveResetTokenStorage claveResetTokenStorage) {
        this.claveResetTokenStorage = claveResetTokenStorage;
    }


    @Override
    public Integer execute(String token) {
        Integer result = claveResetTokenStorage.get(token).getUsuarioId();
        log.debug("Output -> {}", result);
        return result;
    }
}
