package ar.ospim.empleadores.nuevo.app.servicios.empresa;

import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.DeudaNominaMailConfigBO;

public interface DeudaNominaMailConfigService {

	public List<DeudaNominaMailConfigBO> consultar();
	public Optional<DeudaNominaMailConfigBO> consultarVigente();
	
	DeudaNominaMailConfigBO crear(DeudaNominaMailConfigBO reg);
	DeudaNominaMailConfigBO actualizar(Long id, DeudaNominaMailConfigBO reg);

}
