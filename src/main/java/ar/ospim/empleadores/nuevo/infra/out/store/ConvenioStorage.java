package ar.ospim.empleadores.nuevo.infra.out.store;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;

public interface ConvenioStorage {

	public Convenio guardar(Convenio reg);
	public Convenio get(Integer id);
	
}
