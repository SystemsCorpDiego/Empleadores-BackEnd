package ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.auth.usuario.app.ResetearClave;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.resetearclave.ResetearClaveDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.resetearclave.ResetearClaveResponseDto;

@RestController
@RequestMapping("/usuario/clave/resetear")
public class ResetearClaveControllerU {

	private final Logger logger;

	private final ResetearClave resetearClave;

	public ResetearClaveControllerU( ResetearClave resetearClave) {
		this.resetearClave = resetearClave;
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public ResetearClaveResponseDto reset(@Valid @RequestBody ResetearClaveDto passwordResetDto) {
		logger.debug("Reset Clave del token {} ", passwordResetDto.getToken());
		String result =  resetearClave.execute(passwordResetDto.getToken(), passwordResetDto.getClave());
		logger.debug("Username {} -> password reseted", result);
		return new ResetearClaveResponseDto(result);
	}
	
}
