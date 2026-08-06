package ar.ospim.empleadores.nuevo.dominio;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CalculoBO {
	private String tipo;
	private BigDecimal valor;
	private String base; 
}
