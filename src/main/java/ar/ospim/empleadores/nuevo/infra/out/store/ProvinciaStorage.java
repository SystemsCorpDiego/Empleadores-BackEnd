package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.ProvinciaBO;

public interface ProvinciaStorage {

	public List<ProvinciaBO> findAll();   
	public List<ProvinciaBO> findAllOrderByDetalle();
	
}
