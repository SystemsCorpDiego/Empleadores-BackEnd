package ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshTokenDto {
	public final String tokenRefresco;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public RefreshTokenDto(
			@JsonProperty(value = "tokenRefresco", required = true) String tokenRefresco
	) {
		this.tokenRefresco = tokenRefresco;
	}

}
