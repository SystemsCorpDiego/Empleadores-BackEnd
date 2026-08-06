package ar.ospim.empleadores.nuevo.app.servicios.ddjj;

import java.time.LocalDate;

import ar.ospim.empleadores.nuevo.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.dominio.DDJJEmpleadoBO;

public interface DDJJAportesCalcularService {
	
	public void run (DDJJBO ddjj);
	public void run (LocalDate periodo, DDJJEmpleadoBO empleado);
	
}
