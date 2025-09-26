package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Data;

@Data
public class ConvenioDDJJDeudaDto {

	private Long convenioDdjjId; //Contiene deuda_nomina.id
	
	//private Integer id;
	private String id;
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

	public String getIdString() {
		DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");    	
		return CUSTOM_FORMATTER.format(periodo) + aporteCodigo;
	}
    
}
