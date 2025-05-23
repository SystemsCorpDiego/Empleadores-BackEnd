package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ConvenioDDJJDto {
	private Integer id;
	private Integer ddjjId;	
	private LocalDate periodo;
	private Integer secuencia;
	
}
