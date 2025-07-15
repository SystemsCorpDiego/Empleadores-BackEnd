package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ConvenioCuotaChequeConsultaDto {

	private Integer id;
	
	private String numero;
	private LocalDate fecha;
	private BigDecimal importe;
	
	private String estado;
	
}
