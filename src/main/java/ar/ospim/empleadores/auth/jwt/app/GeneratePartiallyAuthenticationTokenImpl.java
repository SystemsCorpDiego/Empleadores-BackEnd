package ar.ospim.empleadores.auth.jwt.app;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.jwt.dominio.JWTokenBo;
import ar.ospim.empleadores.auth.jwt.dominio.TokenTipoEnum;
import ar.ospim.empleadores.comun.token.JWTUtils;

@Service
public class GeneratePartiallyAuthenticationTokenImpl implements GeneratePartiallyAuthenticationToken {

    private final String secret;

    private final Duration tokenExpiration;

    public GeneratePartiallyAuthenticationTokenImpl(
            @Value("${token.secret}") String secret,
            @Value("${token.expiration}") Duration tokenExpiration) {
        this.secret = secret;
        this.tokenExpiration = tokenExpiration;
    }

    @Override
    public JWTokenBo run(Integer userId, String username) {
        String token = createPartiallyAuthenticatedToken(userId, username);
        return new JWTokenBo(token, null);
    }

    private String createPartiallyAuthenticatedToken(Integer userId, String username) {
        Map<String, Object> claims = Map.of(
                "userId", userId,
                JWTUtils.TOKEN_CLAIM_TYPE, TokenTipoEnum.AUTENTICACION_PARCIAL
        );
        return JWTUtils.generate(claims, username, secret, tokenExpiration);
    }
}
