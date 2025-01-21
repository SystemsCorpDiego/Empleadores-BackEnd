package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.CamaraCategoriaBO;

public interface CamaraCategoriaStorage {
	public List<CamaraCategoriaBO> findAll();   
	public Boolean validar(String camara, String categoria);
}
