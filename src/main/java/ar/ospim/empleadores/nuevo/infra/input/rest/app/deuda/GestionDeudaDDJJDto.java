package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class GestionDeudaDDJJDto {
	
	private Integer id;
	private LocalDate periodo;
	private Integer rectificativa;
	private String aporteCodigo;
	private String aporteDescripcion;
    private BigDecimal importe;
    private BigDecimal intereses; 
    
    
    
    
    
    public BigDecimal getImporteTotal() {
    	if ( intereses != null && importe != null ) 
    		return intereses.add(importe);
    	if ( importe != null ) 
    		return importe;
    	return BigDecimal.ZERO;
    }





	public GestionDeudaDDJJDto(Integer id, LocalDate periodo, Integer rectificativa, String aporteCodigo,
			String aporteDescripcion, BigDecimal importe, BigDecimal intereses) {
		super();
		this.id = id;
		this.periodo = periodo;
		this.rectificativa = rectificativa;
		this.aporteCodigo = aporteCodigo;
		this.aporteDescripcion = aporteDescripcion;
		this.importe = importe;
		this.intereses = intereses;
	}
}
