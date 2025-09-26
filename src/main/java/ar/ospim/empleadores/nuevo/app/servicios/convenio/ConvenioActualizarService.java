package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioModiDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;

public interface ConvenioActualizarService {
	
	public Convenio run(ConvenioModiDto dto);
	
}
