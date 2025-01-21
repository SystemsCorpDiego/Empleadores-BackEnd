package ar.ospim.empleadores.nuevo.app.dominio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DDJJEmpleadoBO {

	private Integer id;
	
	@EqualsAndHashCode.Include
	private AfiliadoBO afiliado;
	
	private EmpresaDomicilioBO empresaDomicilio;	
	private String camara;
	private String categoria;
	private LocalDate ingreso;
	
	private BigDecimal remunerativo;
	private BigDecimal noRemunerativo;
	
	private Boolean uomaSocio;
	private Boolean amtimaSocio;
	
	private List<DDJJEmpleadoAporteBO> aportes;
}
