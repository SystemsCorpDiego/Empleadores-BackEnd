package ar.ospim.empleadores.auth.usuario.infra.input.servicio;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.auth.usuario.SgxUserDetails;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;

@Service
public class SgxUserDetailsService implements UserDetailsService {

	private final UsuarioStorage userStorage;

	public SgxUserDetailsService(UsuarioStorage userInfoStorage) {
		this.userStorage = userInfoStorage;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		return loadSgxUserByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
	}

	public Optional<SgxUserDetails> loadSgxUserByUsername(String username) {
		return mapToUserDetails(userStorage.getUsuario(username));
	}

	private Optional<SgxUserDetails> mapToUserDetails(UsuarioBO user) {
		if (user.isHabilitado())
			return Optional.of(toSgxUserDetails(user));
		return Optional.empty();
	}

	private SgxUserDetails toSgxUserDetails(UsuarioBO user) {
		return new SgxUserDetails(user.getId(), user.getDescripcion());
	}
}
