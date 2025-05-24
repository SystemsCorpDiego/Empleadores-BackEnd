package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuota;

@Repository
public interface ConvenioCuotaRepository extends JpaRepository< ConvenioCuota, Integer> {

	public List<ConvenioCuota> findByConvenioId(Integer id);
	
}
