package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IGestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ActaMolineros;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.ActaMolinerosI;

public interface ActaMolinerosRepository extends JpaRepository<ActaMolineros, Integer> {


	@Query(value = "SELECT id, numero, entidad, cuit, estado, fecha, capital, interes, convenio_id, otros, pago FROM public.fGestion_deuda_actas_consulta(:cuit, :entidad)", nativeQuery = true)	
    List<ActaMolineros> getByCuitAndEntidad(String cuit, String entidad);


	@Query(value = "SELECT id, numero, entidad, cuit, estado, fecha, capital, interes, convenio_id, otros, pago, periodos, interes_empleadores  FROM public.fGestion_deuda_actas_consulta2(:cuit, :entidad)", nativeQuery = true)	
    List<ActaMolinerosI> getByCuitAndEntidad2(String cuit, String entidad);

	
	@Query(value = "SELECT id, periodo, rectificativa, aporteCodigo, aporteDescripcion, importe, interes FROM public.fGestion_deuda_periodos_consul(:cuit, :entidad)", nativeQuery = true)	
	List<IGestionDeudaDDJJDto> get(String cuit, String entidad);

	@Query(value = "SELECT devmolineros_acta_periodos_consul as periodos FROM public.devmolineros_acta_periodos_consul(:entidad, :actaId)", nativeQuery = true)	
	String actaPeriodosGet(String entidad, Integer actaId);
	
}
