package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DDJJEmpleadoConsultaDto {
	
	private Integer id;
	private String cuil;
	private String inte;
	private String apellido;
	private String nombre;	 
	private LocalDate fechaIngreso;
	private Integer empresaDomicilioId;
	private String camara;
	private String categoria;
	private BigDecimal remunerativo;
	private BigDecimal noRemunerativo;
	private boolean uomaSocio;
	private boolean amtimaSocio;
	private List< DDJJEmpleadoAporteConsultaDto> aportes;

}
