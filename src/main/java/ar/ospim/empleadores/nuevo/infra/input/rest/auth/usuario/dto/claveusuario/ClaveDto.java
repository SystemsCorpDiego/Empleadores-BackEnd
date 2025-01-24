package ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.claveusuario;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClaveDto {

	@NotNull(message = "{dto.valid.obligado}")
	@NotBlank(message = "{dto.valid.obligado}")
	private String clave;

	@NotNull(message = "{dto.valid.obligado}")
	@NotBlank(message = "{dto.valid.obligado}")
	private String claveNueva;
}
