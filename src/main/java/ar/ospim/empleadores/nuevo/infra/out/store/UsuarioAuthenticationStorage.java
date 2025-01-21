package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.Optional;

public interface UsuarioAuthenticationStorage {

	String getUsername(Integer userId);

	void setTwoFactorAuthenticationSecret(Integer userId, String secret);

	Optional<String> getTwoFactorAuthenticationSecret(Integer userId);

	void enableTwoFactorAuthentication(Integer userId);

	public void disableTwoFactorAuthentication(Integer userId);

	Boolean userHasTwoFactorAuthenticationEnabled(Integer userId);

	String generateSecretKey();

}
