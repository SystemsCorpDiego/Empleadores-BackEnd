package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.PublicacionBO;

public interface PublicacionStorage {

	public PublicacionBO save(PublicacionBO reg);	
	public void deleteById(Integer feriadoId);	
	public List<PublicacionBO> findAll();   

}
