package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjjDeudaNomina;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.ConvenioDdjjDeudaNominaConsultaI;

@Repository
public interface ConvenioDdjjDeudaNominaRepository extends JpaRepository< ConvenioDdjjDeudaNomina, Integer>{

	List<ConvenioDdjjDeudaNomina> findByConvenioDdjjId(Integer id);
	
	@Query(value="select id, boleta_id as boletaId, aporte, aporte_importe as importe, interes, vencimiento FROM convenio_ddjj_deuda_nomina where convenio_ddjj_id = ?1 ", 
			nativeQuery=true)
	List<ConvenioDdjjDeudaNominaConsultaI> findDeudaNomina(Integer convenioDdjjId);
	
}
