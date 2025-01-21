package ar.ospim.empleadores.auth.usuario.infra.output.clave;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.dominio.clave.ClaveEncriptador;

@Service
public class PasswordBCryptImpl implements ClaveEncriptador {

    private final Logger logger;

    private final BCryptPasswordEncoder encoder;

    public PasswordBCryptImpl() {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public String codificar(String rawPassword, String salt, String hashAlgorithm) {
        logger.debug("Encode password");
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean iguales(String password, String password1) {
        logger.debug("Match passwords");
        return encoder.matches(password, password1);
    }
}
