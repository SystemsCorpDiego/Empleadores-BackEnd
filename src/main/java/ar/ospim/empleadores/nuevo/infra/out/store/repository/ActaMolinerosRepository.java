package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IGestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ActaMolineros;

public interface ActaMolinerosRepository extends JpaRepository<ActaMolineros, Integer> {

	@Query(value = "SELECT id, numero, entidad, cuit, estado, fecha, capital, interes, NULL convenio_id, NULL convenio_numero FROM public.fGestion_deuda_actas_consulta(:cuit)", nativeQuery = true)	
    List<ActaMolineros> getByCuit(String cuit);

	@Query(value = "SELECT id, numero, entidad, cuit, estado, fecha, capital, interes, NULL convenio_id, NULL convenio_numero FROM public.fGestion_deuda_actas_consulta(:cuit, :entidad)", nativeQuery = true)	
    List<ActaMolineros> getByCuitAndEntidad(String cuit, String entidad);

	
	@Query(value = "SELECT id, periodo, rectificativa, aporteCodigo, aporteDescripcion, importe, interes FROM public.fGestion_deuda_periodos_consul(:cuit, :entidad)", nativeQuery = true)	
	List<IGestionDeudaDDJJDto> get(String cuit, String entidad);

}
