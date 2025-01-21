package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DDJJEmpleadoAporte;

public interface DDJJEmpleadoAporteRepository extends JpaRepository<DDJJEmpleadoAporte, Integer> {
	
	@Modifying
	@Query(value = "DELETE FROM DDJJEmpleadoAporte a WHERE a.ddjjEmpleado.id = :ddjjEmpleadoId")
	public void deleteByDDJJEmpleadoId(@Param("ddjjEmpleadoId") Integer ddjjEmpleadoId);
	
}
