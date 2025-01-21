package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.AfiliadoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AfiliadoActu;

public interface AfiliadoStorage {

	public List<AfiliadoBO> findByCuil(String cuil);	
	public AfiliadoBO save(AfiliadoBO regBO);
	
	public AfiliadoActu saveActu(AfiliadoActu reg);
	
}
