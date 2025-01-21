package ar.ospim.empleadores.nuevo.app.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DDJJBO {
	
	private Integer id;	
		
	private EmpresaBO empresa;	
	private LocalDate periodo;
	private String estado;
	private Integer secuencia;
	private LocalDate fechaPresentacion;
	private LocalDate fechaCreacion;
	
	private List<DDJJEmpleadoBO> empleados;
	
	List<DDJJEmpleadoBO> lstEmpleadoBaja = new ArrayList<DDJJEmpleadoBO>();
	List<DDJJEmpleadoBO> lstEmpleadoAporteBaja= new ArrayList<DDJJEmpleadoBO>();

}
