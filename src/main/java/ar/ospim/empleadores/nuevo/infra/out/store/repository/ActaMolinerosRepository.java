package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ActaMolineros;

public interface ActaMolinerosRepository extends JpaRepository<ActaMolineros, Integer> {

	@Query(value = "SELECT id, numero, entidad, cuit, estado, fecha, capital, interes, convenio_id, convenio_numero FROM public.fGestion_deuda_actas_consulta(:cuit)", nativeQuery = true)	
    List<ActaMolineros> getByCuit(String cuit);

	@Query(value = "SELECT id, numero, entidad, cuit, estado, fecha, capital, interes, convenio_id, convenio_numero FROM public.fGestion_deuda_actas_consulta(:cuit, :entidad)", nativeQuery = true)	
    List<ActaMolineros> getByCuitAndEntidad(String cuit, String entidad);

}
