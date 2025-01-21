package ar.ospim.empleadores.auth.usuario.app;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResetTwoFA {

	private final UsuarioStorage usuarioStorage;

		public void run(Integer usuarioId) {
			log.debug("Input parameter -> usuarioId {}", usuarioId);
			usuarioStorage.resetDFA(usuarioId);
		}
}
