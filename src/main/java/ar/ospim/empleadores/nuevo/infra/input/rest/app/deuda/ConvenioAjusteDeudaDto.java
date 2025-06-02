package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ConvenioAjusteDeudaDto {
		private Integer convenioAjusteid;
		
		private Integer id;	
		private String motivo;
		private BigDecimal importe;
		private LocalDate vigencia;

}
