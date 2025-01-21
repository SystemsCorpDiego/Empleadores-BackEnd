package ar.ospim.empleadores.auth.jwt.app.login;

import lombok.Getter;

@Getter
public class LoginException extends Exception {

    private final String codigo;

    public LoginException(String codigo, String message) {
        super(message);
        this.codigo = codigo;
    }

}
