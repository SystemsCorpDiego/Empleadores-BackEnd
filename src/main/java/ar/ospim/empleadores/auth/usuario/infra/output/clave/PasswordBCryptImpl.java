package ar.ospim.empleadores.auth.usuario.infra.output.clave;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.impl.DeshabilitarUsuarioImpl;
import ar.ospim.empleadores.auth.usuario.dominio.clave.ClaveEncriptador;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PasswordBCryptImpl implements ClaveEncriptador {

    private final BCryptPasswordEncoder encoder;

    public PasswordBCryptImpl() {
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public String codificar(String rawPassword, String salt, String hashAlgorithm) {
        log.debug("Encode password");
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean iguales(String password, String password1) {
        log.debug("Match passwords");
        return encoder.matches(password, password1);
    }
}
