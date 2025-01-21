package ar.ospim.empleadores.auth.dfa;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.dfa.app.validatedfacode.ValidateDFACode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DFAExternalService {

	private final ValidateDFACode validateTwoFactorAuthenticationCode;

	public Boolean verifyCode(String code) {
		log.debug("Input parameter -> code {}", code);
		Boolean result = this.validateTwoFactorAuthenticationCode.run(code);
		log.debug("Output -> {}", result);
		return result;
	}
}
