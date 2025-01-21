package ar.ospim.empleadores.nuevo.infra.input.rest.app.AfipInteres;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AfipInteresDto {
	   private Integer id;

	   private LocalDate vigenciaDesde;
	   private LocalDate vigenciaHasta;
		
	   private BigDecimal indice;
}
