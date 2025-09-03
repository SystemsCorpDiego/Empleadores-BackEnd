package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioPeriodoDetalle;

public interface ConvenioPeriodoDetalleRepository extends JpaRepository< ConvenioPeriodoDetalle, Integer>  {

	void deleteByConvenioId(Integer convenioId);
	
}
