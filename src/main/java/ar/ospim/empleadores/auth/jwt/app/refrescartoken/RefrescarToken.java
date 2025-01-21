package ar.ospim.empleadores.auth.jwt.app.refrescartoken;

import ar.ospim.empleadores.auth.jwt.app.BadRefreshTokenException;
import ar.ospim.empleadores.auth.jwt.dominio.JWTokenBo;

public interface RefrescarToken {

    JWTokenBo execute(String refreshToken) throws BadRefreshTokenException;
}
