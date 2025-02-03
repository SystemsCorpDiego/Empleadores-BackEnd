package ar.ospim.empleadores.auth.dfa.app.validatedfacode;

import java.util.Locale;
import java.util.Optional;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.dfa.app.CypherStorage;
import ar.ospim.empleadores.auth.dfa.app.DFAExceptionEnum;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.seguridad.UsuarioInfo;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioAuthenticationStorage;
import de.taimos.totp.TOTP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidateDFACode {

	private final UsuarioAuthenticationStorage usuarioAuthenticationStorage;
	private final CypherStorage cypher;
	private final MessageSource messageSource;
	
	private String getTotpCode(String secretKey) {
		Base32 base32 = new Base32();
		byte[] bytes = base32.decode(secretKey);
		String hexKey = Hex.encodeHexString(bytes);
		return TOTP.getOTP(hexKey);
	}

	public boolean run(String code) {
		log.debug("Input parameter -> code {}", code);
		Integer userId = UsuarioInfo.getCurrentAuditor();
		Optional<String> opSecret = usuarioAuthenticationStorage.getTwoFactorAuthenticationSecret(userId);
		if (!opSecret.isPresent()) {
			String errorMsg = messageSource.getMessage(DFAExceptionEnum.DFA_CODE_NULL.getMsgKey(), null, new Locale("es"));	
			throw new BusinessException(DFAExceptionEnum.DFA_CODE_NULL.name(), errorMsg);
		}
		
		
		String decryptedSecret = cypher.decrypt(opSecret.get());
		if ( decryptedSecret.equals(""))
			return false;
		
		String totpCode = getTotpCode(decryptedSecret);
		return totpCode.equals(code);
	}
}
