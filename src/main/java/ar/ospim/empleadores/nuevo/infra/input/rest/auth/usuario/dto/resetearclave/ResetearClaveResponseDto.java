package ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.resetearclave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ResetearClaveResponseDto {
	private final String usuario;
}
