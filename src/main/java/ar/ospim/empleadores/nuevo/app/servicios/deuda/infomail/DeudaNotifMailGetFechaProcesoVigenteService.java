package ar.ospim.empleadores.nuevo.app.servicios.deuda.infomail;

import java.time.LocalDate;
import java.util.Optional;

public interface DeudaNotifMailGetFechaProcesoVigenteService {

	public Optional<LocalDate> run();
	
}
