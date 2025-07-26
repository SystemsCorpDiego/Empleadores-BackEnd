package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ConvenioDDJJDto {
	private Integer id;
	private Integer ddjjId;	
	private LocalDate periodo;
	private Integer secuencia;
	
	private List<ConvenioDDJJDeudaNominaDto> deudaNominas;
}
