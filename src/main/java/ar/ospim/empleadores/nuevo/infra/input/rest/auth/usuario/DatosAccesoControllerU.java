package ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.auth.usuario.app.UpdateUsuarioYClave;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.datosacceso.DatosAccesoDto;

@RestController
@RequestMapping("auth/datos-acceso")
public class DatosAccesoControllerU {
    private final Logger logger;
    private final UpdateUsuarioYClave updateUsernameAndPassword;

    public DatosAccesoControllerU(UpdateUsuarioYClave updateUsernameAndPassword) {
        this.updateUsernameAndPassword = updateUsernameAndPassword;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateAccessData( @Valid @RequestBody DatosAccesoDto accessDataDto) {
        logger.debug("Input -> set access data to user {}", accessDataDto.getUsuario());
        updateUsernameAndPassword.run(accessDataDto.getToken(), accessDataDto.getUsuario(), accessDataDto.getClave());
    }

}
