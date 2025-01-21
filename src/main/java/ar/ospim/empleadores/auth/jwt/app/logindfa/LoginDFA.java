package ar.ospim.empleadores.auth.jwt.app.logindfa;

import ar.ospim.empleadores.auth.jwt.dominio.JWTokenBo;

public interface LoginDFA {

	JWTokenBo execute(String code);

}
