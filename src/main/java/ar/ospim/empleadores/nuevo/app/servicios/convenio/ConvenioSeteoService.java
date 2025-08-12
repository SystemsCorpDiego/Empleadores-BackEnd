package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.time.LocalDate;
import java.util.List;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioSeteo;

public interface ConvenioSeteoService {
	public ConvenioSeteo guardar(ConvenioSeteo aporteSeteo);	
	
	public void borrar(Integer id);	

	public ConvenioSeteo get(Integer id);
	public List<ConvenioSeteo> getAll();
	
	public List<ConvenioSeteo> findVigentes( LocalDate periodo );

}
