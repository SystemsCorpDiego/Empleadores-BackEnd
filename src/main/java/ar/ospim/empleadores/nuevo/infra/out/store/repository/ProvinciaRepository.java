package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Provincia;

public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {

	public List<Provincia> findAllByOrderByDetalle();
	
}
