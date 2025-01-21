package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.BoletaPagoAjuste;;

public interface BoletaPagoAjusteRepository extends JpaRepository<BoletaPagoAjuste, Integer> {

	@Query(value ="select CASE WHEN  COALESCE(max(bpa.id), 0)=0 THEN false ELSE true END "
			+	" from boleta_pago_ajuste bpa, boleta_pago bp "					  
			+  " where  bpa.boleta_pago_id = bp.id "
			+  " and      bp.baja_en is null  "
			+	" and      bpa.ajuste_id = ?1", 
			  nativeQuery = true)
	public Boolean existeBoletaConAjuste(Integer ajusteId);

}
