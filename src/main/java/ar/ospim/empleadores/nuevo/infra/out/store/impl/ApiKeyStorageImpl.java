package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.apikey.dominio.ApiKeyBo;
import ar.ospim.empleadores.nuevo.infra.out.store.ApiKeyStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.UserKeyRepository;

@Service
public class ApiKeyStorageImpl implements ApiKeyStorage {

    private final UserKeyRepository userKeyRepository;

    public ApiKeyStorageImpl(UserKeyRepository userKeyRepository) {
        this.userKeyRepository = userKeyRepository;
    }

    @Override
    public ApiKeyBo getApiKeyInfo(String apiKey) {
        return userKeyRepository.getUserKeyByKey(apiKey)
                .map(userKey -> new ApiKeyBo(userKey.getUserId()))
                .orElse(null);
    }
}
