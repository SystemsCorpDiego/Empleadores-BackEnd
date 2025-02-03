package ar.ospim.empleadores.auth.apikey.app.login;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.apikey.dominio.ApiKeyBo;
import ar.ospim.empleadores.nuevo.infra.out.store.ApiKeyStorage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApiKeyLoginImpl implements ApiKeyLogin {

    private final ApiKeyStorage apiKeyStorage;

    public ApiKeyLoginImpl(ApiKeyStorage apiKeyStorage) {
        this.apiKeyStorage = apiKeyStorage;
    }

    @Override
    public Optional<ApiKeyBo> execute(String apiKey) {
        log.debug("Login by api-key");
        return Optional.ofNullable(apiKeyStorage.getApiKeyInfo(apiKey));
    }

}