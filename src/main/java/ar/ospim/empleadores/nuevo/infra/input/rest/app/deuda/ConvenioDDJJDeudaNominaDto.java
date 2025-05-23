package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ConvenioDDJJDeudaNominaDto {
	private Integer id;
	private Integer boletaId;
	
	private String aporte;
	private String aporteDescripcion;
	private BigDecimal importe;
	private BigDecimal interes;
	private LocalDate vencimiento;
	
}
