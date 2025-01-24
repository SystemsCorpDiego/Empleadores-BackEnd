package ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.resetearclave;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResetearClaveDto {
	@NotNull(message = "{token.obligado}")
	@NotBlank(message = "{token.obligado}")
	private String token;
	@NotNull(message = "{clave.obligado}")
	@NotBlank(message = "{clave.obligado}")
	private String clave;

}
