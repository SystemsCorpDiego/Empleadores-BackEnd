package ar.ospim.empleadores.auth.dfa.app;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.seguridad.UsuarioInfo;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioAuthenticationStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioLogueadoConDFAHabilitado {

	private final UsuarioAuthenticationStorage userAuthenticationStorage;

	public Boolean run() {
		log.debug("No input parameters");
		Integer userId = UsuarioInfo.getCurrentAuditor();
		Boolean result = userAuthenticationStorage.userHasTwoFactorAuthenticationEnabled(userId);
		log.debug("Output -> {}", result);
		return result;
	}
}
