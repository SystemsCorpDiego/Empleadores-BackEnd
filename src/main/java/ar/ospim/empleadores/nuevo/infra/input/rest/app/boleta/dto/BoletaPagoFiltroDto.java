package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BoletaPagoFiltroDto {

	private LocalDate periodoDesde; 
	private LocalDate periodoHasta;
	private LocalDate pagoDesde; 
	private LocalDate pagoHasta;
	private String cuit;
	private Integer empresaId;
	private String concepto;  //Codigo Aporte
	private String entidad;
	private String formaPago;
	
}
