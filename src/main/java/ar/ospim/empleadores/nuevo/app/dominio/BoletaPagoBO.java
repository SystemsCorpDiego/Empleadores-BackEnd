package ar.ospim.empleadores.nuevo.app.dominio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoletaPagoBO {
	private Integer id;
	private EmpresaBO empresa;
	private Integer secuencia;
	private AporteBO aporte;
	private BigDecimal importe = BigDecimal.ZERO;
	
	private LocalDate intencionDePago;
	private String formaDePago; //Ventanilla, Link, PagoMisCuentas. 
	private String bep;  

	private LocalDate vencimiento;
	private BigDecimal interes = BigDecimal.ZERO;
	private List<BoletaPagoAjusteBO> ajustes;
	
	private Integer ddjjId;
	private String actaNro;
	private String descripcion;
	private LocalDate impresion;
	private LocalDate baja;
	
	public BigDecimal getTotalAjustes() {
		BigDecimal rta = BigDecimal.ZERO;
		if ( ajustes != null ) {
			for( BoletaPagoAjusteBO reg: ajustes) {
				if ( reg.getImporte() != null ) {
					rta = rta.add(reg.getImporte() );
				}
			}
		}
		return rta;
	}
}
