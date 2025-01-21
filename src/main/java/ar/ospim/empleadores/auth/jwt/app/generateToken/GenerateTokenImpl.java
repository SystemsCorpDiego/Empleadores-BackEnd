package ar.ospim.empleadores.auth.jwt.app.generateToken;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.jwt.dominio.JWTokenBo;
import ar.ospim.empleadores.auth.jwt.dominio.TokenTipoEnum;
import ar.ospim.empleadores.comun.token.JWTUtils;

@Service
public class GenerateTokenImpl implements GenerateToken {

    private final String secret;

    private final Duration tokenExpiration;

    private final Duration refreshTokenExpiration;

    public GenerateTokenImpl(
            @Value("${token.secret}") String secret,
            @Value("${token.expiration}") Duration tokenExpiration,
            @Value("${refreshToken.expiration}") Duration refreshTokenExpiration) {
        this.secret = secret;
        this.tokenExpiration = tokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    @Override
    public JWTokenBo generateTokens(Integer userId, String username) { 
        String token = createNormalToken(userId, username);
        String refreshToken = createRefreshToken(username);
        return new JWTokenBo(token, refreshToken);
    }

    private String createRefreshToken(String username) {
        Map<String, Object> claims = Map.of(
                JWTUtils.TOKEN_CLAIM_TYPE, TokenTipoEnum.REFRESH
        );
        return JWTUtils.generate(claims, username, secret, refreshTokenExpiration);
    }

    private String createNormalToken(Integer userId, String username) {
        Map<String, Object> claims = Map.of(
                "usuarioId", userId,
                JWTUtils.TOKEN_CLAIM_TYPE, TokenTipoEnum.NORMAL
        );
        return JWTUtils.generate(claims, username, secret, tokenExpiration);
    }
}