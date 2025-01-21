package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Rol;

@Repository
public interface RolRepository  extends JpaRepository<Rol, Short>{

	public Optional<Rol> findByDescripcion(String descripcion);
	
}
