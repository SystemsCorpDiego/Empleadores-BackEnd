package ar.ospim.empleadores.auth.dfa.app;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.seguridad.UsuarioInfo;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioAuthenticationStorage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeshabilitarDFA {
	private final UsuarioAuthenticationStorage usuarioAuthenticationStorage;

	public DeshabilitarDFA(UsuarioAuthenticationStorage usuarioAuthenticationStorage) {
		this.usuarioAuthenticationStorage = usuarioAuthenticationStorage;
	}
	
	public void run() {
		log.debug("Set two factor authentication OFF");
		Integer userId = UsuarioInfo.getCurrentAuditor();
		run(userId);		
	}
	
	public void run(Integer userId) {
		log.debug("Set two factor authentication OFF");
		usuarioAuthenticationStorage.disableTwoFactorAuthentication(userId);
	}
	
}
