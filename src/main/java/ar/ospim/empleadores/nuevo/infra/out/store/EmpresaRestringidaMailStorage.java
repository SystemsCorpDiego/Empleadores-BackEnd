package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaRestringidaMailBO;

public interface EmpresaRestringidaMailStorage {

	public EmpresaRestringidaMailBO save(EmpresaRestringidaMailBO reg);
	public void deleteById(Integer id);
	public List<EmpresaRestringidaMailBO> findAll();
	public Optional<EmpresaRestringidaMailBO> findByCuit(String cuit);
}
