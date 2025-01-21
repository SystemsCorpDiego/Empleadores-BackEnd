package ar.ospim.empleadores.nuevo.app.dominio;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculoBO {
	private String tipo;
	private BigDecimal valor;
	private String base; 
}
