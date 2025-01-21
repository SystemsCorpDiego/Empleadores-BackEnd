package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.EmpresaRestringida;

public interface EmpresaRestringidaRepository extends JpaRepository<EmpresaRestringida, Integer> {

	public Optional<EmpresaRestringida> findByCuit(String cuit);
	
	
	
}
