package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.dominio.ClaveResetTokenBo;
import ar.ospim.empleadores.auth.usuario.dominio.clave.ClaveResetTokenStorageEnumException;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.nuevo.infra.out.store.ClaveResetTokenStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ClaveResetTokenRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ClaveResetToken;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClaveResetTokenStorageImpl implements ClaveResetTokenStorage {


    private final ClaveResetTokenRepository claveResetTokenRepository;

    @Value("${password.reset.token.expiration}")
    private Duration passwordExpiration;

    public ClaveResetTokenStorageImpl(ClaveResetTokenRepository passwordResetTokenRepository) {
        this.claveResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public ClaveResetTokenBo get(String token) {
        log.debug("Get password reset token from database {} ", token);
        ClaveResetToken passwordResetToken = claveResetTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new BusinessException(ClaveResetTokenStorageEnumException.TOKEN_NOT_FOUND.name(), String.format("Token %s inexistente ", token)));
        return mapPasswordResetTokenBo(passwordResetToken);
    }

    @Override
    public ClaveResetTokenBo crearToken(Integer userId) {
        log.debug("Create password reset token for userId {} ",userId);
        ClaveResetToken entity = new ClaveResetToken();
        entity.setUsuarioId(userId);
        entity.setBajaFecha(LocalDateTime.now().plusSeconds(passwordExpiration.getSeconds()));
        entity.setToken(UUID.randomUUID().toString());
        entity.setHabilitado(true);
        ClaveResetToken passwordResetToken = claveResetTokenRepository.save(entity);
        return mapPasswordResetTokenBo(passwordResetToken);
    }

    private ClaveResetTokenBo mapPasswordResetTokenBo(ClaveResetToken claveResetToken) {
        return new ClaveResetTokenBo(claveResetToken.getId(),
        		claveResetToken.getToken(),
        		claveResetToken.getUsuarioId(),
        		claveResetToken.getHabilitado(),
        		claveResetToken.getBajaFecha());
    }

    @Override
    public void deshabilitarTokens(Integer userId) {
        log.debug("Disable token to userId {} ", userId);
        claveResetTokenRepository.deshabilitarTokens(userId);
    }
}
