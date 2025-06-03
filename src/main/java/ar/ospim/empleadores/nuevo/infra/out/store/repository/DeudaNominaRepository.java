package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.IGestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNomina;

public interface DeudaNominaRepository extends JpaRepository<DeudaNomina, Long> {
	
	@Query(value = "SELECT id, periodo, rectificativa, aporteCodigo, aporteDescripcion, importe, interes FROM public.fGestion_deuda_periodos_consul(:cuit)", nativeQuery = true)	
	List<IGestionDeudaDDJJDto> get(String cuit);

	@Query(value = "SELECT id, periodo, rectificativa, aporteCodigo, aporteDescripcion, importe, interes FROM public.fGestion_deuda_periodos_consul(:cuit, :entidad)", nativeQuery = true)	
	List<IGestionDeudaDDJJDto> get(String cuit, String entidad);
		
	@Query(value = "SELECT a FROM DeudaNomina a WHERE a.ddjjId = :ddjjId and a.actaId IS NULL and a.aporte.entidad = :entidad")	
	List<DeudaNomina> findByDdjjIdAndEntidadAndActaIdIsNull(Integer ddjjId, String entidad);
	
	
	@Query(value = "SELECT * FROM fdeudanomina_actualizar(?1);", nativeQuery = true)	
	void actualizarCuit( @Param("p_cuit")  String p_cuit);
	
}
