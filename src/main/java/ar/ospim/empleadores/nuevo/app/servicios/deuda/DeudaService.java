package ar.ospim.empleadores.nuevo.app.servicios.deuda;

import java.util.List;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.GestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ActaMolineros;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNomina;

public interface DeudaService {
	public List<ActaMolineros>  getMolinerosActas(Integer empresaId);
	public List<DeudaNomina>  getDDJJ(Integer empresaId);
	
	public List<GestionDeudaDDJJDto> actualizarSecuencia(List<GestionDeudaDDJJDto> lst);	
	public List<GestionDeudaDDJJDto> actualizarAporteDescrip(List<GestionDeudaDDJJDto> lst);	
	
}
