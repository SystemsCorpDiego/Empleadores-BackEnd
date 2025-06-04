package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ConvenioActaDto {
	private Integer id;
	private Integer actaId;
	private String numero;
	private String estado;
	private LocalDate fecha;
	private BigDecimal capital;
	private BigDecimal interes;
	
	public BigDecimal getImporteTotal() {
		if ( capital != null && interes != null)
			return capital.add(interes);
		if ( capital != null )
			return capital;
		
		return BigDecimal.ZERO;
	}
	
}
