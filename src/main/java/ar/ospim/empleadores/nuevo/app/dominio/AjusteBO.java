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
public class AjusteBO {
	private Integer id;
	private EmpresaBO empresa;
	private AporteBO aporte;
	private LocalDate periodo;
	private BigDecimal importe;
	private LocalDate vigencia;
	private String motivo;
	
	private List<BoletaPagoAjusteBO> boletas;
	
	public String getBoleta() {
		String aux = "";
		for ( BoletaPagoAjusteBO reg:  boletas) {
			if ( reg.getBoletaPago()!=null && reg.getBoletaPago().getSecuencia()!=null ) {
				aux += ", "+reg.getBoletaPago().getSecuencia();
			}
		}
		
		if ( !"".equals(aux) ) {
			aux = aux.substring(2);
		} else {
			aux = null;
		}
		
		return aux;
	}
}
