package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ActaMolineros;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNomina;

public interface DeudaStorage {

	public List<ActaMolineros> getActasMolineros(String cuit);
	public List<DeudaNomina> getNomina(String cuit);
}
