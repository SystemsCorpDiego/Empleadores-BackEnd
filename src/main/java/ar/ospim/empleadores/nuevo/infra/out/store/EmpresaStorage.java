package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;

public interface EmpresaStorage {	
	
	public EmpresaBO save(EmpresaBO regBO);
	public  Optional<EmpresaBO> findById(Integer id);
	public  Optional<EmpresaBO> findByCuit(String cuit);
	
}
