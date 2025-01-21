package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.RolFuncionalidad;

@Repository
public interface RolFuncionalidadRepository   extends JpaRepository<RolFuncionalidad, Integer> {

	public Optional<RolFuncionalidad> findByRolAndFuncionalidad(String rol, String funcionalidad);
	public List<RolFuncionalidad> findByRol(String rol);
	
	public List<RolFuncionalidad> deleteByRol(String rol);
}
