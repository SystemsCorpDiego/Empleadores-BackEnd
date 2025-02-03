package ar.ospim.empleadores.auth.usuario.app.impl;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.ActualizarClave;
import ar.ospim.empleadores.auth.usuario.dominio.clave.ClaveEncriptador;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActualizarClaveImpl implements ActualizarClave  {

    private final UsuarioStorage usuarioStorage;
    
    private final ClaveEncriptador claveEncriptador;
    
    public ActualizarClaveImpl(UsuarioStorage usuarioStorage,
    		ClaveEncriptador claveEncriptador) {
        this.claveEncriptador = claveEncriptador;
        this.usuarioStorage = usuarioStorage;
    }
    
    @Override
    public void execute(String username, String password) {
        log.debug("Update password username -> {}", username);
        UsuarioBO usuario = usuarioStorage.getUsuario(username);
        final var salt = "salt";
        final var hashAlgorithm = "hashAlgorithm";
        usuario.setUsuarioClaveBo(claveEncriptador.codificar(password, salt, hashAlgorithm),
                salt,
                hashAlgorithm);
        usuarioStorage.actualizar(usuario);
    }
}
