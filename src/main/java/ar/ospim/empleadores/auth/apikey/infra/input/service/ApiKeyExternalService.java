package ar.ospim.empleadores.auth.apikey.infra.input.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.apikey.app.login.ApiKeyLogin;

@Service
public class ApiKeyExternalService {

    private final ApiKeyLogin apiKeyLogin;

    public ApiKeyExternalService(ApiKeyLogin apiKeyLogin) {
        this.apiKeyLogin = apiKeyLogin;
    }

    public Optional<ApiKeyInfoDto> login(String apiKey){
        return apiKeyLogin.execute(apiKey)
                .map( apiKeyBo -> new ApiKeyInfoDto(apiKeyBo.getId()));
    }
}
