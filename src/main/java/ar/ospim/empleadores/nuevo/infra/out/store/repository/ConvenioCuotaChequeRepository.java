package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuotaCheque;

@Repository
public interface ConvenioCuotaChequeRepository extends JpaRepository< ConvenioCuotaCheque, Integer> {
	
	public List<ConvenioCuotaCheque> findByConvenioCuotaConvenioIdAndConvenioCuotaId(Integer  convenioId, Integer id);	

	public List<ConvenioCuotaCheque> findByConvenioCuotaId(Integer id);	
	
	public Long deleteByConvenioCuotaId(Integer ConvenioCuotaId);
	
}
