package ar.ospim.empleadores.nuevo.infra.out.store;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.DeudaNominaMailConfigBO;

public interface DeudaNominaMailConfigStorage {

	Optional<DeudaNominaMailConfigBO> findVigente();
	List<DeudaNominaMailConfigBO> findAll();
	DeudaNominaMailConfigBO save(DeudaNominaMailConfigBO reg);
	Optional<DeudaNominaMailConfigBO> findByFecha(LocalDate fechaProceso);
		
	Optional<LocalDate> getFechaProcesoVigente();

}
