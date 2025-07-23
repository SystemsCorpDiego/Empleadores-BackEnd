package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ConvenioActaDeudaDto {
 
	    private Integer convenioActaId;
	    
		private Integer id;		
		private String nroActa;
		private String estadoDeuda;
		private LocalDate fechaActa;
		private BigDecimal importe;
		private BigDecimal intereses;
		
		public BigDecimal getImporteTotal() {
			if ( importe != null && intereses != null)
				return importe.add(intereses);
			if ( importe != null )
				return importe;
			
			return BigDecimal.ZERO;
		}
		
}
