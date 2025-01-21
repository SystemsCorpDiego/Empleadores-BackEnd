package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Ajuste;

public interface AjusteRepository  extends JpaRepository<Ajuste, Integer> {
	
	@Query(value =" select * FROM ajuste a "
			+ " where a.empresa_id = ?1 "
			+ " and     a.aporte          = ?2 "
			+ " and     a.vigencia     <= ?3 "
			+ " and     ( (          a.importe > 0 "
			+ "                 AND NOT EXISTS(SELECT 1 from boleta_pago_ajuste bpa, boleta_pago bp"
			+ "                                              where  bpa.boleta_pago_id = bp.id "
			+ "                                              and      bp.baja_en is null  "
			+ "                                              and      bpa.ajuste_id = a.id ) "
			+ "               ) "
			+ "               OR a.importe < (select COALESCE(sum(bpa.importe),0) "
			+ "                                          from boleta_pago_ajuste bpa, boleta_pago bp "
			+ "                                          where  bpa.boleta_pago_id = bp.id "
			+ "                                          and      bp.baja_en is null  "
			+ "                                          and      bpa.ajuste_id = a.id ) "
			+ "            ) "
			+ " order by a.importe desc "	
			, 
			nativeQuery = true)
	List<Ajuste>  consultarAportesVigentes(Integer empresaId, String aporte, LocalDate vigencia);
	
	
	@Query(value =" select a from Ajuste a "
			+ " where  a.motivo <> 'IPF' ")
	List<Ajuste>  consultarCrud();
	
	@Query(value =" select COALESCE(sum(bpa.importe),0) from boleta_pago_ajuste bpa, boleta_pago bp "
								+ " where  bpa.boleta_pago_id = bp.id "
								+ " and      bp.baja_en is null  "
								+ " and      bpa.ajuste_id = ?1  ", 
			nativeQuery = true)
	public BigDecimal importeUsado(Integer ajusteId); 

	
	@Transactional 
	@Modifying(clearAutomatically = true)
	@Procedure(value = "Ajuste.generarAjusteAutomaticoIPF")
	void generarAjusteAutomaticoIPF(@Param("p_aporte")  String p_aporte, @Param("p_empresa_id")  Integer p_empresa_id);

	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "CALL generarAjusteAutomaticoIPF(?1, ?2);", nativeQuery = true)
	void generarAjusteAutomaticoIPF22(@Param("p_aporte")  String p_aporte, @Param("p_empresa_id")  Integer p_empresa_id);
	
}
