package ar.ospim.empleadores.nuevo.infra.out.store.repository.querys;

import java.math.BigDecimal;
import java.time.LocalDate;

import ar.ospim.empleadores.nuevo.app.servicios.formapago.FormaPagoService;
import ar.ospim.empleadores.nuevo.app.servicios.formapago.FormaPagoServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor 
public class BoletaPagoDDJJConsulta {
	private Integer id; 	
	
	private Integer empresaId; 
	private String cuit; 
	private String razonSocial; 
	
	private String aporte; //Concepto
    private String aporteDescripcion; //Concepto descrip    
    private LocalDate vencimiento; //TODO:falta calcular !!    
    private LocalDate periodo;     
    
    private BigDecimal importe;
    private BigDecimal interes;
    private BigDecimal ajuste;
    
    private LocalDate intencionDePago;    
    private LocalDate fechaDePago;    
    private BigDecimal importeRecibido;
    
    private String formaDePago; //codigo de FormaPago
    private String formaDePagoDescripcion; //Descripcion de FormaPago
    private String bep; 
    
    private Integer secuenciaDdjj;   
    private String tipoDdjj; //convertir secuenciaDdjj => "Original",
    
    private Integer secuenciaBoleta;    
    private Integer ddjjId;
    private LocalDate baja;
    
    public BigDecimal getTotalFinal() {
    	return this.getImporte().add(this.getInteres()).add(this.getAjuste());
    }
    
    public Boolean getRequiereBep() {
    	FormaPagoService fpService = new FormaPagoServiceImpl();
    	if ( (this.getBep() ==null) && (fpService.generaVEP(this.getFormaDePago()) )  ) 
    			return true;
    	return false;    			
    }
}
