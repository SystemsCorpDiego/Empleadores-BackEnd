package ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.auth.jwt.app.login.LoginJWTImpl;
import ar.ospim.empleadores.auth.usuario.app.CrearTokenClave;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario.dto.TokenResetClaveDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
//@Tag(name = "User password reset", description = "User password reset")
//@PreAuthorize("hasPermission(#institutionId, 'ADMINISTRADOR_INSTITUCIONAL_BACKOFFICE')")
public class GenerarTokenResetClaveControllerU {
    private final CrearTokenClave crearTokenClave;

    public GenerarTokenResetClaveControllerU(CrearTokenClave crearTokenClave) {
        this.crearTokenClave = crearTokenClave;
    }

    @PostMapping(value = "/usuario/{usuarioId}/clave/token-reset")
    @ResponseStatus(HttpStatus.OK)
    public TokenResetClaveDto create(@PathVariable(name = "usuarioId") Integer usuarioId) {
    	log.debug("Input parametros -> {}", usuarioId);
        String result = crearTokenClave.run(usuarioId);
        log.debug("Output -> result {}", result);
        return new TokenResetClaveDto(result);
    }
    
}
