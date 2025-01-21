package ar.ospim.empleadores.auth.dfa.app;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.dfa.dominio.SetDFABo;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.seguridad.UsuarioInfo;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioAuthenticationStorage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GenerarDFA {
	private final MessageSource messageSource;
	private final UsuarioAuthenticationStorage userAuthenticationStorage;
	private final CypherStorage cypher;
	private final String issuer;

	public GenerarDFA(UsuarioAuthenticationStorage userAuthenticationStorage,
										   CypherStorage cypher,
										   @Value("${app.seguridad.dfa.issuer:EMPLE}") String issuer,
										   MessageSource messageSource) {
		this.userAuthenticationStorage = userAuthenticationStorage;
		this.cypher = cypher;
		this.issuer = issuer;
		this.messageSource = messageSource;
	}

	public SetDFABo run() {		
		Integer userId = UsuarioInfo.getCurrentAuditor();
		return run(userId);		
	}

	public SetDFABo run(Integer usuarioId) {
		log.debug("Set two factor authentication");
		if (userAuthenticationStorage.userHasTwoFactorAuthenticationEnabled(usuarioId)) {
			String errorMsg = messageSource.getMessage(DFAExceptionEnum.DFA_HABILITADO_PREVIAMENTE.getMsgKey(), null, new Locale("es"));	
			throw new BusinessException(DFAExceptionEnum.DFA_HABILITADO_PREVIAMENTE.name(), errorMsg);
		}
		String username = userAuthenticationStorage.getUsername(usuarioId);
		String secretKey = userAuthenticationStorage.generateSecretKey();
		String encryptedSecret = cypher.encrypt(secretKey);
		userAuthenticationStorage.setTwoFactorAuthenticationSecret(usuarioId, encryptedSecret);
		SetDFABo result = new SetDFABo(username, issuer, secretKey);
		log.debug("Output -> {}", result);
		return result;
	}
	
}
