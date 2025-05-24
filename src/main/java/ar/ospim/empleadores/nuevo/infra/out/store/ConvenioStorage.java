package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.ConvenioConsultaFiltro;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;

public interface ConvenioStorage {

	public Convenio getById(Integer id);
	public Convenio guardar(Convenio reg);	
	public Convenio get(Integer id);
	public List<Convenio> get(ConvenioConsultaFiltro filtro);
	
}
