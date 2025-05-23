package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ConvenioConsultaDto {
	private String cuit;
	private String razonSocial;
	private LocalDate fecha;
	private Integer id;
	private BigDecimal capital;
	private BigDecimal interes;
	private BigDecimal saldoFavor;
	private Integer cuotas;
	private String medioPago;
	private String estado;
	
}
