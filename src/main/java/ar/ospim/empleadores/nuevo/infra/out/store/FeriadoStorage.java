package ar.ospim.empleadores.nuevo.infra.out.store;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.FeriadoBO;

public interface FeriadoStorage {

	public Optional<FeriadoBO> get(LocalDate fecha);	
	public List<FeriadoBO> find(LocalDate desde);
	public List<FeriadoBO> find(LocalDate desde, LocalDate hasta);
	public List<FeriadoBO> findAll();   
	public FeriadoBO save(FeriadoBO feriado);	
	public void deleteById(Integer feriadoId);	
	
}
