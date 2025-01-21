package ar.ospim.empleadores.nuevo.infra.out.store;

import ar.ospim.empleadores.auth.apikey.dominio.ApiKeyBo;

public interface ApiKeyStorage {

    ApiKeyBo getApiKeyInfo(String apiKey);
}
