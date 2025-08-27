package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Feriado;

@Repository
public interface FeriadoRepository  extends JpaRepository<Feriado, Integer> {

	@Query(value ="select f from Feriado f "
			+ "order by f.fecha "
			)
	public List<Feriado> findAllOrderByFechaDesc();
	                   
	
	@Query(value ="select f from Feriado f "
			+ "where f.fecha >  ?1  "
			//+ "and d.periodo BETWEEN CAST(?2 AS TIMESTAMP)  and CAST(?3 AS TIMESTAMP)  "
			)
	public List<Feriado> getFeriadosDesde(LocalDate fecha);
	
	public Optional<Feriado> findByFecha(LocalDate fecha); 

	@Query(value ="select f from Feriado f "
			+ "where f.fecha BETWEEN ?1 and ?2 "
			+ " order by fecha desc "
			)
	public  List<Feriado> getFeriados(LocalDate desde, LocalDate hasta);
}
