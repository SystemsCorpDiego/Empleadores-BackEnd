package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ConvenioConsultaDto {
	private Integer id;
	private String entidad;
	private String numero;
	
	private Integer empresaId;
	private String cuit;
	private String razonSocial;
	private LocalDate fecha;
	private BigDecimal capital;
	private BigDecimal interes;
	private BigDecimal saldoFavor;
	private Integer cuotas;
	private String medioPago;
	private String estado;
	private String usuario;
	
}
