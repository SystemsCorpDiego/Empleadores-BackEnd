package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AfipInteres;

public interface AfipInteresRepository   extends JpaRepository<AfipInteres, Integer>{
	
	List<AfipInteres> findAllByOrderByDesdeDesc();
	
	@Query(value ="select i FROM AfipInteres i "
			+ "where i.desde =  ?1 "
			)
	Optional<AfipInteres> findByDesde(LocalDate desde);

	@Query(value ="select * FROM afip_interes i "
			+ " where ?1 BETWEEN i.desde and COALESCE(i.hasta, ?1) "
			+ " order by i.id "
			+ " LIMIT 1 ",
			nativeQuery = true
			)
	Optional<AfipInteres> findContenido(LocalDate desde);

	@Query(value ="select * FROM afip_interes i "
			+ " where ?1 BETWEEN i.desde and COALESCE(i.hasta, ?1) "
			+ " and     id <> ?2 "
			+ " order by i.id "
			+ " LIMIT 1 ",
			nativeQuery = true
			)
	Optional<AfipInteres> findContenido(LocalDate desde, Integer id);
	
	
	@Query( value ="select getintereses as interes from public.getintereses( ?1, ?2, cast(?3 as timestamp) , cast(?4 as timestamp) )" ,
			nativeQuery = true)
	BigDecimal calcularInteres(BigDecimal capital, BigDecimal interes, LocalDate desde, LocalDate hasta);
	
}
