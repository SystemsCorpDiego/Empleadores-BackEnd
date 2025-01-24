package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DDJJBoletaPagoModiDto {
	
	private LocalDate fechaDePago;
	
}
