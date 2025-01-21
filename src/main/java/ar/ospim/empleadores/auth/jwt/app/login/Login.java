package ar.ospim.empleadores.auth.jwt.app.login;

import ar.ospim.empleadores.auth.jwt.dominio.JWTokenBo;
import ar.ospim.empleadores.auth.jwt.dominio.LoginBo;

public interface Login {
	
    JWTokenBo execute(LoginBo login) throws LoginException;
}
