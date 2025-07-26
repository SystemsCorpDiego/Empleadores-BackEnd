package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ConvenioAjusteDeudaDto {
		private Integer convenioAjusteId;
		
		private Integer id;	
		private String motivo;
		private BigDecimal importe;
		private LocalDate vigencia;

}
