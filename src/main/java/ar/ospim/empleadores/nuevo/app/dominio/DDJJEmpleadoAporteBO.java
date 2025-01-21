package ar.ospim.empleadores.nuevo.app.dominio;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter 
@ToString
public class DDJJEmpleadoAporteBO {
	private Integer id;
	private AporteBO aporte;
	private BigDecimal importe;
}
