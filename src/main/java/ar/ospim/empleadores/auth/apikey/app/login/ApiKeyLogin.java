package ar.ospim.empleadores.auth.apikey.app.login;

import java.util.Optional;

import ar.ospim.empleadores.auth.apikey.dominio.ApiKeyBo;

public interface ApiKeyLogin {

    Optional<ApiKeyBo> execute(String apiKey);
}
