package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.RolBO;

public interface RolStorage {
	
	public RolBO save(RolBO reg);	
	public void deleteById(Short id);	
	public List<RolBO> findAll();      
	public List<RolBO> findTipoUsuario();
	public Optional<RolBO> findById(Short id);
	public Optional<RolBO> findByDescripcion(String descripcion);

}
