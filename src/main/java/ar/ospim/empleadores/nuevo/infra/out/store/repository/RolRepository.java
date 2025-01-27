package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Rol;

@Repository
public interface RolRepository  extends JpaRepository<Rol, Short>{

	@Query(value ="select b from Rol b  where  b.id > 3")
	public List<Rol> findRolTipoUsuario();
	
	public Optional<Rol> findByDescripcion(String descripcion);
	
}
