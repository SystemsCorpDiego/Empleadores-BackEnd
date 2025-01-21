package ar.ospim.empleadores.auth.dfa.app;

import java.util.Optional;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.seguridad.UsuarioInfo;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioAuthenticationStorage;
import de.taimos.totp.TOTP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidarCodigoDFA {

	private final UsuarioAuthenticationStorage userAuthenticationStorage;
	private final CypherStorage cypher;

	private String getTotpCode(String secretKey) {
		Base32 base32 = new Base32();
		byte[] bytes = base32.decode(secretKey);
		String hexKey = Hex.encodeHexString(bytes);
		return TOTP.getOTP(hexKey);
	}

	public boolean run(String code) {
		log.debug("Input parameter -> code {}", code);
		Integer userId = UsuarioInfo.getCurrentAuditor();
		
		return run(code, userId);		
	}
	
	public boolean run(String code, Integer userId) {
		Optional<String> opSecret = userAuthenticationStorage.getTwoFactorAuthenticationSecret(userId);
		if (opSecret.isPresent()) {
			String decryptedSecret = cypher.decrypt(opSecret.get());
			String totpCode = getTotpCode(decryptedSecret);
			return totpCode.equals(code);
		}
		return true;		
	}
}
