package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DDJJBoletaArmadoDetalleDto {
    
	public DDJJBoletaArmadoDetalleDto(String codigo) {
		super();
		this.codigo = codigo;
	}

	private Integer id; //TODO: lo agregue para las Modificaciones 
	
    @JsonProperty("numero_boleta")
    private Integer numeroBoleta;

	private String codigo; //Concepto
    
    private String descripcion; //Concepto descrip
    
    private LocalDate vencimiento; //TODO: falta completarlo
    
    private LocalDate periodo;

    private BigDecimal interes;  
    
    private LocalDate intencionDePago;
    
    private String formaDePago; //Descripcion de FormaPago
    private String formaDePagoDescripcion; //Descripcion de FormaPago
    
    @JsonProperty("declaracion_jurada_id")
    private Integer declaracionJuradaId;

    @JsonProperty("tipo_ddjj")
    private String tipoDdjj; //"Original",
    
    private LocalDate baja;
    
    private List<DDJJBoletaArmadoDetalleAfiliadoDto> afiliados;
    
    private List<DDJJBoletaArmadoDetalleAjusteDto> ajustes;
    
    public BigDecimal getAjuste() {
    	BigDecimal total = BigDecimal.ZERO;
    	if ( this.ajustes != null ) {
	    	for (DDJJBoletaArmadoDetalleAjusteDto reg: this.ajustes) {
	    		total = total.add(reg.getMonto());
	    	}    	
    	}
    	return total;
    }
    
    //Es el total del Importe de todos los Aportes
    public BigDecimal getTotal_acumulado() { 
	    //@JsonProperty("total_acumulado")
	    //private BigDecimal totalAcumulado;
	    BigDecimal total = BigDecimal.ZERO;
    	if ( this.afiliados != null ) {
	    	for (DDJJBoletaArmadoDetalleAfiliadoDto reg: this.afiliados) {
	    		total = total.add(reg.getCapital());
	    	}    	
    	}
    	return total;
    }

    ////TODO: TotalAcumulado+Intereses+Ajustes
    public BigDecimal getTotal_final() { 
    	//@JsonProperty("total_final")
    	BigDecimal totalAcumulado = this.getTotal_acumulado();
    	BigDecimal totalAjustes = this.getAjuste();
    	BigDecimal interes = BigDecimal.ZERO;
    	if ( this.interes != null)
    		interes = this.interes;
    			
    	return interes.add(totalAcumulado).add(totalAjustes);
    }
    
    
	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DDJJBoletaArmadoDetalleDto other = (DDJJBoletaArmadoDetalleDto) obj;
		return Objects.equals(codigo, other.codigo);
	}
}
