package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoletaPagoConsDDJJAfiDto {
	private String cuil;
	private String apellido;
	private String nombre;
	private BigDecimal remunerativo;
	private BigDecimal capital;
}
