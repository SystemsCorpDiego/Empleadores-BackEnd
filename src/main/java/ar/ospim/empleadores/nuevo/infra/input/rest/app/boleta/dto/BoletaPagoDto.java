package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoletaPagoDto {
	private Integer id;
	private String razonDePago;
	
	private LocalDate intencionDePago;
	private String entidad;
	private String nroActa;
	private BigDecimal importe;
}
