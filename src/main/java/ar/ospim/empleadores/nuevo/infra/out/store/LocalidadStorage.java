package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.LocalidadBO;

public interface LocalidadStorage {
	public List<LocalidadBO> findAll();   
	public List<LocalidadBO> findAllOrderByDescripcion();
	public List<LocalidadBO> findByProvinciaId(Integer provinciaId);   
	public List<LocalidadBO> findByProvinciaIdOrderByDescripcion(Integer provinciaId);
	
}
