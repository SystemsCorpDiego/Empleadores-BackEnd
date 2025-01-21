package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Localidad;

public interface LocalidadRepository extends JpaRepository<Localidad, Integer> {
	
	
	List<Localidad> findAllByOrderByDetalleAsc();
	List<Localidad> findByProvinciaId(Integer provinciaId);
	List<Localidad> findByProvinciaIdOrderByDetalleAsc(Integer provinciaId);
	
}
