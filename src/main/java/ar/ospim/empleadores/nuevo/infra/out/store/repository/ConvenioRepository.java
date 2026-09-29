package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;

@Repository
public interface ConvenioRepository  extends JpaRepository< Convenio, Integer>  {
	
	public List<Convenio> findByEmpresaId(Integer empresaId);
	public List<Convenio> findByEmpresaIdAndEstado(Integer empresaId, String estado);
	
	@Query(value =" select * FROM convenio "
			+ " where empresa_id = ?1 "
			+ " and creado_en between ?2 and ?3 ", nativeQuery = true)
	public List<Convenio> findByEmpresaIdAndCreatedOnBetween(Integer empresaId, LocalDateTime desde, LocalDateTime hasta);
	
	@Query(value =" select * FROM convenio "
			+ " where empresa_id = ?1 "
			+ " and estado = ?2"
			+ " and creado_en between ?3 and ?4 ", nativeQuery = true)
	public List<Convenio> findByEmpresaIdAndEstadoAndCreatedOnBetween(Integer empresaId, String estado, LocalDateTime desde, LocalDateTime hasta);
	
	public List<Convenio> findByEmpresaIdAndEstadoAndEntidad(Integer empresaId, String estado, String entidad); 
	
	@Query(value =" select * FROM convenio "
			+ " where creado_en between ?1 and ?2 ", nativeQuery = true)
	public List<Convenio> findByCreatedOnBetween(LocalDateTime desde, LocalDateTime hasta);

	@Query(value =" select * FROM convenio "
			+ " where creado_en between ?1 and ?2 "
			+ " and estado = ?3", nativeQuery = true)
	public List<Convenio> findByCreatedOnBetweenAndEstado(LocalDateTime desde, LocalDateTime hasta, String estado);
	
	public List<Convenio> findByEstado(String estado);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "CALL convenio_importes_modi(?1, ?2, ?3, ?4);", nativeQuery = true)
	void actualizarImportes(@Param("p_convenio_id")  Integer convenioId, @Param("p_imp_deuda")  BigDecimal p_imp_deuda, @Param("p_imp_interes")  BigDecimal p_imp_interes, @Param("p_imp_saldo_favor")  BigDecimal p_imp_saldo_favor );

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "CALL convenio_modo_pago_modi(?1, ?2, ?3);", nativeQuery = true)
	void actualizarModoPago(@Param("p_convenio_id")  Integer convenioId, @Param("p_cuotas_canti")  Integer p_cuotas_canti, @Param("p_intencion_pago")  LocalDate p_intencion_pago);

}
