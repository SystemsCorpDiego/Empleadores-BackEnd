package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BepDto {
	private String bep;
	private String error;
}
