package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.time.LocalDate;
import java.util.List;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.ConvenioAltaDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;

public interface ConvenioService {

	public Convenio generar(ConvenioAltaDto dto);
	
	public Convenio get(Integer empresaId, Integer convenioId);
	
	public List<Convenio> get(Integer empresaId, LocalDate desde, LocalDate hasta);
	
	
}
