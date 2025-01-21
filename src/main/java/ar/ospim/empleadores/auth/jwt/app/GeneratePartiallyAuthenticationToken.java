package ar.ospim.empleadores.auth.jwt.app;

import ar.ospim.empleadores.auth.jwt.dominio.JWTokenBo;

public interface GeneratePartiallyAuthenticationToken {

    JWTokenBo run(Integer userId, String username);

}
