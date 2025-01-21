package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.security.SecureRandom;
import java.util.Optional;

import org.apache.commons.codec.binary.Base32;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioAuthenticationStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioAuthenticationStorageImpl implements UsuarioAuthenticationStorage {

	private final UsuarioRepository userRepository;

	@Override
	public String getUsername(Integer userId) {
		log.debug("Input parameter -> userId {}", userId);
		String result = userRepository.getById(userId).getDescripcion();
		log.debug("Output -> {}", result);
		return result;
	}

	@Override
	public void setTwoFactorAuthenticationSecret(Integer userId, String secret) {
		log.debug("Input parameters -> userId {}, secret {}", userId, secret);
		userRepository.setDFASecreto(userId, secret);
	}

	@Override
	public Optional<String> getTwoFactorAuthenticationSecret(Integer userId) {
		log.debug("Input parameter -> userId {}", userId);
		return userRepository.getDfaSecreto(userId);
	}

	@Override
	public void enableTwoFactorAuthentication(Integer userId) {
		log.debug("Input parameters -> userId {}", userId);
		userRepository.habilitarDFA(userId);
	}
	
	@Override
	public void disableTwoFactorAuthentication(Integer userId) {
		log.debug("Input parameters -> userId {}", userId);
		userRepository.deshabilitarDFA(userId);
	}

	@Override
	public Boolean userHasTwoFactorAuthenticationEnabled(Integer userId) {
		log.debug("Input parameters -> userId {}", userId);
		return userRepository.usuarioConDFAHabilitado(userId);
	}

	@Override
	public String generateSecretKey() {
		log.debug("Generate secret key");
		return this.generateKey();
	}

	private String generateKey() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		Base32 base32 = new Base32();
		return base32.encodeToString(bytes);
	}
	
}
