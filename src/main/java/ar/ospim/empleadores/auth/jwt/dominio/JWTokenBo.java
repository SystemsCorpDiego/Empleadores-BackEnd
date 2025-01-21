package ar.ospim.empleadores.auth.jwt.dominio;

public class JWTokenBo {

	public final String token;
	public final String tokenRefresco;

	public JWTokenBo(String token, String tokenRefresco) {
		this.token = token;
		this.tokenRefresco = tokenRefresco;
	}

}
