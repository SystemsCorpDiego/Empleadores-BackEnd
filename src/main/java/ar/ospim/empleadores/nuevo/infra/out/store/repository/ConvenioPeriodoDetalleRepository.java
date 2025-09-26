package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioPeriodoDetalle;

public interface ConvenioPeriodoDetalleRepository extends JpaRepository< ConvenioPeriodoDetalle, Integer>  {

	void deleteByConvenioId(Integer convenioId);
	
	public Optional<ConvenioPeriodoDetalle> findByConvenioIdAndPeriodoAndAporte(Integer convenioId, LocalDate periodo, String aporte);
}
