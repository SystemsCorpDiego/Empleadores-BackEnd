package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioActa;

@Repository
public interface ConvenioActaRepository   extends JpaRepository< ConvenioActa, Integer>  {

	List<ConvenioActa> findByConvenioId(Integer convenioId);
	
}
