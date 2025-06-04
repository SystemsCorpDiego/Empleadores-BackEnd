package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ConvenioAjusteDto {
	private Integer id;
	private Integer ajusteId;
	
	private LocalDate vigencia;
	private String motivo;
	private BigDecimal importe;
	
}
