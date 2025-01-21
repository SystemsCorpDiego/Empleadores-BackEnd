package ar.ospim.empleadores.auth.apikey.app.login;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.apikey.dominio.ApiKeyBo;
import ar.ospim.empleadores.nuevo.infra.out.store.ApiKeyStorage;

@Service
public class ApiKeyLoginImpl implements ApiKeyLogin {

    private final Logger logger;

    private final ApiKeyStorage apiKeyStorage;

    public ApiKeyLoginImpl(ApiKeyStorage apiKeyStorage) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.apiKeyStorage = apiKeyStorage;
    }

    @Override
    public Optional<ApiKeyBo> execute(String apiKey) {
        logger.debug("Login by api-key");
        return Optional.ofNullable(apiKeyStorage.getApiKeyInfo(apiKey));
    }

}