package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.CamaraBO;

public interface CamaraStorage {
	public List<CamaraBO> findAll();   
	public Optional<CamaraBO> findByCodigo(String codigo);
}
