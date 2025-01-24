package ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TokenResetClaveDto {
	public String token;
}
