package ar.ospim.empleadores.auth.dfa.app;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.seguridad.UsuarioInfo;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioAuthenticationStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfirmarDFA {

	private final ValidarCodigoDFA ValidarCodigoDFA;
	private final UsuarioAuthenticationStorage UsuarioAuthenticationStorage;

	public Boolean run(String code) {
		log.debug("Input parameter -> code {}", code);
		Boolean validated = ValidarCodigoDFA.run(code);
		if (!validated) {
			return false;
		}
		UsuarioAuthenticationStorage.enableTwoFactorAuthentication(UsuarioInfo.getCurrentAuditor());
		return true;
	}
	
	public Boolean run(String code, Integer usuarioId) {
		log.debug("Input parameter -> code {}", code);
		Boolean validated = ValidarCodigoDFA.run(code);
		if (!validated) {
			return false;
		}
		UsuarioAuthenticationStorage.enableTwoFactorAuthentication(usuarioId);
		return true;
	}
	
	
}
