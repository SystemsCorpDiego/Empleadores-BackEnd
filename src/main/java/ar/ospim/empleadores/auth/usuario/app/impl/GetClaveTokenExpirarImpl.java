package ar.ospim.empleadores.auth.usuario.app.impl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.GetClaveTokenExpirar;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GetClaveTokenExpirarImpl implements GetClaveTokenExpirar {

    @Value("${password.reset.token.expiration}")
    private Duration passwordTokenExpiration;

    public long execute() {
        log.debug("No input parameters");
        long result = passwordTokenExpiration.toHours();
        log.debug("Output -> {}", result);
        return result;
    }
    
}
