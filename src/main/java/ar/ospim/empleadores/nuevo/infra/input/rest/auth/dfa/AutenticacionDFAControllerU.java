package ar.ospim.empleadores.nuevo.infra.input.rest.auth.dfa;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.auth.dfa.app.ConfirmarDFA;
import ar.ospim.empleadores.auth.dfa.app.DeshabilitarDFA;
import ar.ospim.empleadores.auth.dfa.app.GenerarDFA;
import ar.ospim.empleadores.auth.dfa.app.UsuarioLogueadoConDFAHabilitado;
import ar.ospim.empleadores.auth.dfa.dominio.SetDFABo;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.dfa.dto.BooleanDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.dfa.dto.CodigoVerificacionDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.dfa.dto.DFADto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth/dfa")
//@Tag(name = "Two-Factor Authentication", description = "Two-Factor Authentication")
@Slf4j
@RequiredArgsConstructor
public class AutenticacionDFAControllerU {

	private final GenerarDFA generateTwoFactorAuthentication;
	private final ConfirmarDFA confirmTwoFactorAuthentication;
	private final UsuarioLogueadoConDFAHabilitado usuarioLogueadoConDFAHabilitado;
	private final DeshabilitarDFA deshabilitarDFA;
	
	@PostMapping()
	public ResponseEntity<DFADto> generateTwoFactorAuthenticationCodes() {
		log.debug("Generate Two-factor Authentication codes");
		SetDFABo authenticationBo = generateTwoFactorAuthentication.run();
		DFADto result = new DFADto(authenticationBo.generateAuthenticatorBarCode(), authenticationBo.getSharedSecret());
		log.debug("Output -> {}", result);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/confirmar")
	public ResponseEntity<Boolean> confirmTwoFactorAuthentication(@RequestBody CodigoVerificacionDto codigoVerificacionDto) {
		log.debug("Confirm Two-factor Authentication");
		Boolean result = confirmTwoFactorAuthentication.run(codigoVerificacionDto.getCodigo());
		log.debug("Output -> {}", result);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/usuario-loguedo-habilitado")
	public ResponseEntity<BooleanDto> userHasTwoFactorAuthenticationEnabled() {
		log.debug("Confirm Two-factor Authentication");
		Boolean valor = usuarioLogueadoConDFAHabilitado.run();
		BooleanDto result = new BooleanDto(valor); 
		log.debug("Output -> {}", result);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/deshabilitar")
	public ResponseEntity<Boolean> disableTwoFactorAuthenticationCodes() {
		log.debug("Disable Two-factor Authentication");
		deshabilitarDFA.run();
		return ResponseEntity.ok(null);
	}

}
