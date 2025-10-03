package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IGestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ActaMolineros;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.ActaMolinerosI;

public interface DeudaStorage {

	public List<ActaMolinerosI> getActasMolineros2(String cuit, String entidad); 
	public List<ActaMolineros> getActasMolineros(String cuit, String entidad); 

	public List<IGestionDeudaDDJJDto> getNominaDto(String cuit, String entidad);
	
	public void actualizarCuit( String p_cuit );
}
