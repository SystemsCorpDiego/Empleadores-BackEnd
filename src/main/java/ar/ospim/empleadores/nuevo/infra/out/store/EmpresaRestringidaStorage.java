package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaRestringidaBO;

public interface EmpresaRestringidaStorage {
	
	public EmpresaRestringidaBO save(EmpresaRestringidaBO reg);	
	public void deleteById(Integer id);	
	public List<EmpresaRestringidaBO> findAll();   
	public Optional<EmpresaRestringidaBO> findByCuit(String cuit);
}
