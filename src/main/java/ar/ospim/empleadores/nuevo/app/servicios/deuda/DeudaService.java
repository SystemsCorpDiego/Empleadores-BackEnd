package ar.ospim.empleadores.nuevo.app.servicios.deuda;

import java.util.List;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.GestionDeudaActaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.GestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IGestionDeudaAjustesDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IGestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ActaMolineros;

public interface DeudaService {
	
	public List<ActaMolineros>  getMolinerosActas(Integer empresaId);
	public List<ActaMolineros>  getMolinerosActas(Integer empresaId, String entidad); 
	public List<IGestionDeudaDDJJDto>  getDDJJDto(Integer empresaId);
	public List<IGestionDeudaDDJJDto>  getDDJJDto(Integer empresaId, String entidad);	 
	
	public List<IGestionDeudaAjustesDto>  getAjustesDto(Integer empresaId, String entidad);	 
	
}
