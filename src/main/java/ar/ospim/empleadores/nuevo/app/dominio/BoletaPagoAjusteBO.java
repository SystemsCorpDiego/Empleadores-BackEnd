package ar.ospim.empleadores.nuevo.app.dominio;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoletaPagoAjusteBO {
	private Integer id;
	private BoletaPagoBO boletaPago;
	private AjusteBO ajuste;
	private BigDecimal importe;
	
	public BoletaPagoAjusteBO(BoletaPagoBO boletaPago, AjusteBO ajuste, BigDecimal importe) {
		super();
		this.boletaPago = boletaPago;
		this.ajuste = ajuste;
		this.importe = importe;
	}

	public BoletaPagoAjusteBO() {
		super();
	}
	
}
