package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Data;

@Data
public class GestionDeudaDDJJDto {
	
	private Integer convenioDdjjId;
	
	//private Integer id;
	private String id;
	private LocalDate periodo;
	private Integer rectificativa;
	private String aporteCodigo;
	private String aporteDescripcion;
    private BigDecimal importe;
    private BigDecimal intereses;
    private BigDecimal pago; 
    
    
    public BigDecimal getImporteTotal() {
    	BigDecimal imp  =  importe;
    	if ( importe == null ) 
    		imp  =  BigDecimal.ZERO;
    	BigDecimal inter  =  intereses;
    	if ( inter == null ) 
    		inter  =  BigDecimal.ZERO;
    	BigDecimal p =  pago;
    	if ( p == null ) 
    		p =  BigDecimal.ZERO;
    	
    	return imp.add(inter).add( p.negate() ) ;
    }


	public GestionDeudaDDJJDto(String id, LocalDate periodo, Integer rectificativa, String aporteCodigo,
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
	
	public String getIdString() {
		DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");    	
		return CUSTOM_FORMATTER.format(periodo) + aporteCodigo;
	}

}
