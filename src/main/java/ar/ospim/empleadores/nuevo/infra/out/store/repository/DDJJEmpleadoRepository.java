package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DDJJEmpleado;

@Repository
public interface DDJJEmpleadoRepository extends JpaRepository<DDJJEmpleado, Integer> {
	
	/*
	@Modifying 
	@Query("delete from DDJJEmpleadoAporte a where a.ddjjEmpleado.id =:empleadoId")
	public void borrarAportes(Integer empleadoId);
	 */
}
