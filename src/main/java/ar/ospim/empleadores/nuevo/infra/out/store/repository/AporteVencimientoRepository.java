package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AporteVencimiento;

public interface AporteVencimientoRepository extends JpaRepository<AporteVencimiento, Integer>  {
	public List<AporteVencimiento> findAll();
	
	public List<AporteVencimiento> findByAporteCodigoOrderByDesdeDesc(String aporteCodigo);
	
	@Query(value ="select * FROM aporte_vencimiento i "
			+ " where ?2 BETWEEN i.desde and COALESCE(i.hasta, ?2) "
			+ " and i.aporte = ?1 "
			+ " order by i.desde desc "
			+ " LIMIT 1 ",
			nativeQuery = true
			)
	Optional<AporteVencimiento> findContenido(String aporte, LocalDate desde);
	
	@Query(value ="select * FROM aporte_vencimiento i "
			+ " where ?2 BETWEEN i.desde and COALESCE(i.hasta, ?2) "
			+ " and i.aporte = ?1 "
			+ " and i.id <> ?3 "
			+ " order by i.desde desc "
			+ " LIMIT 1 ",
			nativeQuery = true
			)
	Optional<AporteVencimiento> findContenido(String aporte, LocalDate desde, Integer id);

}
