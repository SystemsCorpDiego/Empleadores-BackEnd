package ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class JWTokenDto {
	public final String token;
	public final String tokenRefresco;

	public JWTokenDto(String token, String tokenRefresco) {
		this.token = token;
		this.tokenRefresco = tokenRefresco;
	}

}
