package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.RolFuncionalidad;

public interface RolFuncionalidadStorage {
	public RolFuncionalidad save(RolFuncionalidad reg);	
	public List<RolFuncionalidad> findAll();      
	public List<RolFuncionalidad> findByRol(String rol);
}
