package ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.auth.usuario.app.UpdateClavePropia;
import ar.ospim.empleadores.comun.seguridad.UsuarioInfo;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.claveusuario.ClaveDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/usuario/clave")
//@Tag(name = "Password", description = "Password")
@Validated
public class UsuarioClaveActualizarControllerU {

	private final UpdateClavePropia updateClavePropia;

	public UsuarioClaveActualizarControllerU(UpdateClavePropia updateClavePropia) {
		this.updateClavePropia = updateClavePropia;
	}

	@PatchMapping
	@ResponseStatus(HttpStatus.OK)
	public void updatePassword(@NotNull @RequestBody ClaveDto passwordDto) {
		log.debug("{}", "actualizarClave valida");
		var userId = UsuarioInfo.getCurrentAuditor();
		updateClavePropia.execute(userId, passwordDto.getClave(), passwordDto.getClaveNueva());
		log.debug("{}", "Clave Actualizada");
	}
	
}
