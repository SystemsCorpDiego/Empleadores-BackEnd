package ar.ospim.empleadores.auth.jwt.infra.output.dfa;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.dfa.DFAExternalService;
import ar.ospim.empleadores.auth.jwt.app.logindfa.DFAStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DFAStorageImpl implements DFAStorage {

	private final DFAExternalService twoFactorAuthenticationExternalService;

	@Override
	public Boolean verifyCode(String code) {
		return twoFactorAuthenticationExternalService.verifyCode(code);
	}

}
