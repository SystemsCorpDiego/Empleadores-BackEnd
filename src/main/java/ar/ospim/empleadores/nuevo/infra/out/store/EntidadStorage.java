package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;

import ar.ospim.empleadores.nuevo.dominio.EntidadBO;

public interface EntidadStorage {
	public List<EntidadBO> findAll();   
}
