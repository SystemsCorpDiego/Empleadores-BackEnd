package ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.auth.usuario.app.CrearTokenClave;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario.dto.TokenResetClaveDto;

@RestController
//@Tag(name = "User password reset", description = "User password reset")
//@PreAuthorize("hasPermission(#institutionId, 'ADMINISTRADOR_INSTITUCIONAL_BACKOFFICE')")
public class GenerarTokenResetClaveControllerU {
    private final Logger logger;
    private final CrearTokenClave crearTokenClave;

    public GenerarTokenResetClaveControllerU(CrearTokenClave crearTokenClave) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.crearTokenClave = crearTokenClave;
    }

    @PostMapping(value = "/usuario/{usuarioId}/clave/token-reset")
    @ResponseStatus(HttpStatus.OK)
    public TokenResetClaveDto create(@PathVariable(name = "usuarioId") Integer usuarioId) {
    	logger.debug("Input parametros -> {}", usuarioId);
        String result = crearTokenClave.run(usuarioId);
        logger.debug("Output -> result {}", result);
        return new TokenResetClaveDto(result);
    }
    
}
