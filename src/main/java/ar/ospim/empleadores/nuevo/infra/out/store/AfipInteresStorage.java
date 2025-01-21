package ar.ospim.empleadores.nuevo.infra.out.store;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.AfipInteresBO;

public interface AfipInteresStorage {
	public List<AfipInteresBO> findAll();
	public List<AfipInteresBO> findAllByOrderByDesdeDesc();
	public AfipInteresBO save(AfipInteresBO aporte);	
	public void deleteById(Integer id);	
	public AfipInteresBO findById(Integer id);	
	public Optional<AfipInteresBO> findByDesde(LocalDate desde);
	public Optional<AfipInteresBO> findContenido(LocalDate desde);
}
