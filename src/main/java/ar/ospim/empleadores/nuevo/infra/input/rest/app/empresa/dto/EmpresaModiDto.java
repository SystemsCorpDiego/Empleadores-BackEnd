package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmpresaModiDto {

	@NotBlank(message = "{dto.valid.obligado}")
	private String razonSocial;
	
	private boolean actividad_molinera;
		
}
