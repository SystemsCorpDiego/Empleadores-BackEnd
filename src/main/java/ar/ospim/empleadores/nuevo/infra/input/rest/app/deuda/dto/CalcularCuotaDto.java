package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class CalcularCuotaDto {
	private BigDecimal importeDeuda;
	private Integer cantidadCuota;
	private LocalDate fechaIntencionPago;
	
	private BigDecimal importeCuota;
	private BigDecimal importeInteresTotal;
	
}
