package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioSeteo;

@Repository
public interface ConvenioSeteoRepository extends JpaRepository< ConvenioSeteo, Integer>{

	
	@Query(value ="select * FROM convenio_seteo i "
			+ " where ?1 BETWEEN i.desde and COALESCE(i.hasta, ?1) "
			+ " order by i.id "
			+ " LIMIT 1 ",
			nativeQuery = true
			)
	Optional<ConvenioSeteo> findContenido(LocalDate desde);

	@Query(value ="select * FROM convenio_seteo i "
			+ " where ?1 BETWEEN i.desde and COALESCE(i.hasta, ?1) "
			+ " and     i.id <> ?2 "
			+ " order by i.id "
			+ " LIMIT 1 ",
			nativeQuery = true
			)
	Optional<ConvenioSeteo> findContenido(LocalDate desde, Integer id);

	
	@Query(value ="select * FROM convenio_seteo i "
			+ " where i.cuit = ?1 "
			+ " and     ?2 BETWEEN i.desde and COALESCE(i.hasta, ?2) "
			+ " order by i.id "
			+ " LIMIT 1 ",
			nativeQuery = true
			)
	Optional<ConvenioSeteo> findContenido(String cuit, LocalDate desde);

	@Query(value ="select * FROM convenio_seteo i "
			+ " where i.cuit = ?1 "
			+ " and     ?2 BETWEEN i.desde and COALESCE(i.hasta, ?2) "
			+ " and     i.id <> ?3 "
			+ " order by i.id "
			+ " LIMIT 1 ",
			nativeQuery = true
			)
	Optional<ConvenioSeteo> findContenido(String cuit, LocalDate desde, Integer id);

}
