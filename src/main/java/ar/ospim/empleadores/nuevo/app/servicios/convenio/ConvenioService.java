package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.ConvenioAltaDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;

public interface ConvenioService {

	public Convenio generar(ConvenioAltaDto dto);
	
	
}
