package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ConvenioDDJJDeudaDto {

	private Integer convenioDdjjId;
	
	private Integer id;
	private LocalDate periodo;
	private Integer rectificativa;
	private String aporteCodigo;
	private String aporteDescripcion;
	private BigDecimal importe;
	private BigDecimal intereses;
	
	public BigDecimal getImporteTotal() {
		if ( importe != null && intereses != null )
			return importe.add(intereses);
		if ( importe != null )
			return importe;
		return 	BigDecimal.ZERO;
	}
	 
    
}
