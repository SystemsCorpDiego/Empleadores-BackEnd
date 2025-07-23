package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DeudaActaDto {
	private Long id;
	private String numero;
	private String estado;
	private String fecha;
	private BigDecimal capital;
	private BigDecimal interes;
	private BigDecimal importeTotal;
	
}
