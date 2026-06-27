package ar.ospim.empleadores.nuevo.infra.out.store;

import ar.ospim.empleadores.nuevo.app.dominio.DeudaNominaMailConfigBO;

public interface DeudaNominaMailConfigStorage {

	DeudaNominaMailConfigBO findFirst();
	DeudaNominaMailConfigBO save(DeudaNominaMailConfigBO reg);

}
