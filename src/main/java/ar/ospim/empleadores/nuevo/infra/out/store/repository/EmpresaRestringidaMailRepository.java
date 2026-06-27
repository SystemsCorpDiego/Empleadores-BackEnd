package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.EmpresaRestringidaMail;

public interface EmpresaRestringidaMailRepository extends JpaRepository<EmpresaRestringidaMail, Integer> {

	public Optional<EmpresaRestringidaMail> findByCuit(String cuit);

}
