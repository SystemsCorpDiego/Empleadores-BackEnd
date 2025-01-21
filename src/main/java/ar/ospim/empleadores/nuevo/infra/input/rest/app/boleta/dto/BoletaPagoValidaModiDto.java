package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoletaPagoValidaModiDto {
	private Boolean reemplazar;
	private Boolean bep;
	private Boolean impresa;
}
