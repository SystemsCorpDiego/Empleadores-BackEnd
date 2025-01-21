package ar.ospim.empleadores.auth.jwt.infra.output.token;

import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import ar.ospim.empleadores.auth.jwt.dominio.TokenData;
import ar.ospim.empleadores.auth.jwt.dominio.TokenTipoEnum;
import ar.ospim.empleadores.comun.token.JWTUtils;

public class TokenUtils {

	public static final String TOKEN_CLAIM_TYPE = "tokentype";

	private TokenUtils() {}

	private static Date generateExpirationDate(Duration expiration) {
		return new Date(System.currentTimeMillis() + expiration.toMillis());
	}

	public static Optional<TokenData> parseToken(String token, String secret, TokenTipoEnum expectedType) {
		return JWTUtils.parseClaims(token, secret)
				.filter(claims -> isTokenType(expectedType, claims))
				.map(
						claims -> new TokenData(
								expectedType,
								claims.get("sub").toString(),
								(Integer)claims.get("usuarioId")
						)
				);
	}

	public static boolean isTokenType(TokenTipoEnum expected, Map<String, Object> claims) {
		Object found = claims.get(TOKEN_CLAIM_TYPE);
		return (expected.toString().equals(found));
	}
}
