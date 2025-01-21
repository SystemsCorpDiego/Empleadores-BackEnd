package ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.auth.usuario.app.GetClaveTokenExpirar;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
//@Tag(name = "Password token expiration", description = "Password token expiration")
public class TokenLoginExpirarControllerU {

    private GetClaveTokenExpirar getPasswordTokenExpiration;

    public TokenLoginExpirarControllerU(GetClaveTokenExpirar getPasswordTokenExpiration){
        this.getPasswordTokenExpiration = getPasswordTokenExpiration;
    }

    @GetMapping("auth/config/token-login-expirar")
    public long get(){
        log.debug("No input parameters");
        long result = getPasswordTokenExpiration.execute();
        log.debug("Output -> {}", result);
        return result;
    }
}
