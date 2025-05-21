package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ConvenioActaDto {
	private Integer id;
	private Integer actaId;
	private String numero;
	private BigDecimal capital;
	private BigDecimal interes;
	
}
