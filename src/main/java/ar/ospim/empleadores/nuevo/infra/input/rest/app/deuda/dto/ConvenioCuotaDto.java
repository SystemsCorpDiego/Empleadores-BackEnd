package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ConvenioCuotaDto {
	
	private Integer id;
	private Integer numero;
	private BigDecimal importe;
	private BigDecimal interes;
	private LocalDate vencimiento;
	
	
}
