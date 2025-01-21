package ar.ospim.empleadores.auth.usuario.app.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.UpdateClave;
import ar.ospim.empleadores.auth.usuario.dominio.clave.ClaveEncriptador;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;

@Service
public class UpdateClaveImpl implements UpdateClave {

    private final Logger logger;

    private final UsuarioStorage userStorage;

    private final ClaveEncriptador passwordEncryptor;
    
    public UpdateClaveImpl(UsuarioStorage userStorage,
    		ClaveEncriptador passwordEncryptor) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.passwordEncryptor = passwordEncryptor;
        this.userStorage = userStorage;
    }

    @Override
    public void execute(String username, String password) {
        logger.debug("Update password username -> {}", username);
        UsuarioBO user = userStorage.getUsuario(username);
        final var salt = "salt";
        final var hashAlgorithm = "hashAlgorithm";
        user.setUsuarioClaveBo(passwordEncryptor.codificar(password, salt, hashAlgorithm),
                salt,
                hashAlgorithm);
        userStorage.actualizar(user);
    }
}
