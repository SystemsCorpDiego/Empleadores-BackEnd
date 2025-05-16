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

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.IGestionDeudaAjustes;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Ajuste;

public interface AjusteRepository  extends JpaRepository<Ajuste, Integer> {
	
	@Query(value =" select * FROM ajuste a "
			+ " where a.empresa_id = ?1 "
			+ " and     a.aporte          = ?2 "
			+ " and     a.vigencia     <= ?3 "
			+ " and     a.vigencia     >= '2024-01-01' "
			+ " and     ( ( a.importe > 0 AND fajuste_importe_utilizado( a.id ) = 0 ) "
			+ "               OR "
			+ "               ( a.importe < 0 AND  a.importe < public.fajuste_importe_utilizado( a.id ) ) "
			+ "            ) "
			+ " order by a.importe desc "	
			, 
			nativeQuery = true)
	List<Ajuste>  consultarAportesVigentes(Integer empresaId, String aporte, LocalDate vigencia);
	
	
	@Query(value =" select a from Ajuste a "
			+ " where  a.motivo <> 'IPF' ")
	List<Ajuste>  consultarCrud();
	
	@Query(value =" select a from Ajuste a "
			+ " where  a.motivo <> 'IPF' and a.empresa.cuit = :cuit "
			+ " order by a.vigencia desc " )
	List<Ajuste>  consultarCrudPorCuit(@Param("cuit") String cuit);
	
	@Query(value =" select * FROM fajuste_importe_utilizado( :ajusteId ) ", 
			nativeQuery = true)
	public BigDecimal importeUsado(Integer ajusteId); 

	
	@Query(value =" select * FROM fGestion_deuda_ajuste_consul( :empresaId, :entidad) ", 
			nativeQuery = true)
	public List<IGestionDeudaAjustes> getGestionDeudaAjustes(Integer empresaId, String entidad);
	
	@Transactional 
	@Modifying(clearAutomatically = true)
	@Procedure(value = "Ajuste.generarAjusteAutomaticoIPF")
	void generarAjusteAutomaticoIPF(@Param("p_aporte")  String p_aporte, @Param("p_empresa_id")  Integer p_empresa_id);

	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "CALL generarAjusteAutomaticoIPF(?1, ?2);", nativeQuery = true)
	void generarAjusteAutomaticoIPF22(@Param("p_aporte")  String p_aporte, @Param("p_empresa_id")  Integer p_empresa_id);
	
}
