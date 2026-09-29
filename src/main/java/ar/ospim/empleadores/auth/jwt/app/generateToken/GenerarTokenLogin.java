package ar.ospim.empleadores.auth.jwt.app.generateToken;

import ar.ospim.empleadores.auth.jwt.dominio.JWTokenBo;

public interface GenerarTokenLogin {

    JWTokenBo generateTokens(Integer userId, String username);
}
