package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ConvenioCuotaChequeAltaDto {
	
	private Integer empresaId;
	private Integer convenioId;
	private Integer cuotaId;
	private Integer chequeId;
	
	private String numero;
	private LocalDate fecha;
	private BigDecimal importe;
}
