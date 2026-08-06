package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.dominio.MailTipoEmpresaRestringidaBO;

public interface EmpresaRestringidaMailStorage {

	public MailTipoEmpresaRestringidaBO save(MailTipoEmpresaRestringidaBO reg);
	public void deleteById(Integer id);
	public List<MailTipoEmpresaRestringidaBO> findAll();
	public Optional<MailTipoEmpresaRestringidaBO> findByCuit(String cuit);
}
