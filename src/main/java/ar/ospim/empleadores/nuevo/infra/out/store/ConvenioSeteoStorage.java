package ar.ospim.empleadores.nuevo.infra.out.store;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioSeteo;

public interface ConvenioSeteoStorage {
	
	public ConvenioSeteo save(ConvenioSeteo aporte);
	public void deleteById(Integer id);	
	public ConvenioSeteo get(Integer id);
	public List<ConvenioSeteo> getAll();
	
	public Optional<ConvenioSeteo> findContenido(String cuit, LocalDate desde);
	public Optional<ConvenioSeteo> findContenido(String cuit, LocalDate desde, Integer id);
	public Optional<ConvenioSeteo> findContenido(LocalDate desde);
	public Optional<ConvenioSeteo> findContenido(LocalDate desde, Integer id);
	public Optional<ConvenioSeteo> findContenidoGeneral(LocalDate desde);
	
	public ConvenioSeteo findVigente(String codigo, LocalDate periodo);
	
	public List<ConvenioSeteo> getVigentes(LocalDate periodo);

}
