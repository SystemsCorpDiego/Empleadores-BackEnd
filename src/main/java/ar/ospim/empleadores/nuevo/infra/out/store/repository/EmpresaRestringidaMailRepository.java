package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.MailTipoEmpresaRestringida;

public interface EmpresaRestringidaMailRepository extends JpaRepository<MailTipoEmpresaRestringida, Integer> {

	public Optional<MailTipoEmpresaRestringida> findByCuit(String cuit);

}
