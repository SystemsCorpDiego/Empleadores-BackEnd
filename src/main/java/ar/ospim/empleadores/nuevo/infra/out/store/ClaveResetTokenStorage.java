package ar.ospim.empleadores.nuevo.infra.out.store;

import ar.ospim.empleadores.auth.usuario.dominio.ClaveResetTokenBo;

public interface ClaveResetTokenStorage {

    ClaveResetTokenBo get(String token);

    void deshabilitarTokens(Integer userId);

    ClaveResetTokenBo crearToken(Integer userId);
}
