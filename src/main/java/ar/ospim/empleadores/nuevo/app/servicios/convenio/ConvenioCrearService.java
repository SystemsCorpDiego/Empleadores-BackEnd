package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioAltaDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;

public interface ConvenioCrearService {

	public Convenio run(ConvenioAltaDto dto);
	
}
