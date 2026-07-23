package ar.ospim.empleadores.nuevo.app.servicios.empresa;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNominaMailEnvio;

public interface DeudaNotifMailRegistrarService {

	DeudaNominaMailEnvio run(DeudaNominaMailEnvio reg);
	
}
