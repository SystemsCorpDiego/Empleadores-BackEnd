package ar.ospim.empleadores.auth.usuario.app;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ObtenerUsuarioConDFAHabilitado {

    private final UsuarioStorage usuarioStorage;

    public Boolean run(Integer userId) {
        log.debug("Input parameter -> userId {}", userId);
        return usuarioStorage.usuarioConDFAHabilitado(userId);
    }
}
