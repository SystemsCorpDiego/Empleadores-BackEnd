package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PlanPagoDto {
	private LocalDate intencionPago;
	private Integer cantidadCuota;
}
