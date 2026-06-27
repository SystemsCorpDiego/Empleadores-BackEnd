package ar.ospim.empleadores.nuevo.app.servicios.empresa;

import ar.ospim.empleadores.nuevo.app.dominio.DeudaNominaMailConfigBO;

public interface DeudaNominaMailConfigService {

	DeudaNominaMailConfigBO consultar();
	DeudaNominaMailConfigBO crear(DeudaNominaMailConfigBO reg);
	DeudaNominaMailConfigBO actualizar(Long id, DeudaNominaMailConfigBO reg);

}
