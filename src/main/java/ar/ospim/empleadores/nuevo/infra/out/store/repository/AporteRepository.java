package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Aporte;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.BancoConvenioMovimiento;

public interface AporteRepository extends JpaRepository<Aporte, String> {
	
	@Transactional
	@Modifying
    @Query("delete from Aporte a where a.codigo = ?1")
    void deleteByCodigo(String codigo);
	
	Optional<Aporte> getByCodigo(String codigo);
	Optional<Aporte> getByEntidadAndDdjjAndCodigoNot(String entidad, Boolean ddjj, String codigo);
	
	
	List<Aporte> findAllByOrderByOrdenAsc();
	
	List<Aporte> findByDdjjTrueOrderByOrdenAsc(); 
	
	@Query(value ="select * FROM aporte a"
							+ "where exists ( select * FROM aporte_seteo s"
										+ "			   where  s.aporte = a.codigo"
										+ "			   and    s.desde <= ?1 "
										+ "			   and    COALESCE(s.hasta, ?1 ) >= ?1 "
										+ "			  )", 
				nativeQuery = true)
	List<Aporte> getVigentes(LocalDate periodo);
	
	@Query("select a  FROM BancoConvenioMovimiento a where a.aporte.codigo = ?1 " )
	BancoConvenioMovimiento getMovimientoBancario(String aporteCodigo);
	
}
