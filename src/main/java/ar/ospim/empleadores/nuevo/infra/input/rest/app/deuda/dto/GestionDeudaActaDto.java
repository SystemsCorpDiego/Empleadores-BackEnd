package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class GestionDeudaActaDto {

	private Integer convenioActaId;
	
	private Integer id;
    private String estadoDeuda;                       
    private String nroActa;
    private LocalDate fechaActa;
    private BigDecimal importe;
    private BigDecimal intereses;
    private String periodos;
    
    public BigDecimal getImporteTotal() {
    	if ( intereses != null && importe != null ) 
    		return intereses.add(importe);
    	if ( importe != null ) 
    		return importe;
    	return BigDecimal.ZERO;
    }
    
}
