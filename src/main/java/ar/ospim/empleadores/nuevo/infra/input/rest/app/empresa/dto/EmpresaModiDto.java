package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaModiDto {

	@NotBlank(message = "{dto.valid.obligado}")
	private String razonSocial;
	
	private boolean actividad_molinera;
		
}
