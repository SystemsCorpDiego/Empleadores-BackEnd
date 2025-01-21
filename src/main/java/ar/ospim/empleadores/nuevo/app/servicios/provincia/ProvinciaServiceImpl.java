package ar.ospim.empleadores.nuevo.app.servicios.provincia;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.ProvinciaBO;
import ar.ospim.empleadores.nuevo.infra.out.store.ProvinciaStorage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProvinciaServiceImpl implements ProvinciaService {

	private final ProvinciaStorage provinciaStorage;
	
	@Override
	public List<ProvinciaBO> consulta() {
    	//List<RamoBO> lst = new ArrayList<RamoBO>();
    	List<ProvinciaBO> lst = provinciaStorage.findAll();
    	return lst;
	}
	
	@Override
	public List<ProvinciaBO> consultaOrderByDetalle() {
		List<ProvinciaBO> lst = provinciaStorage.findAllOrderByDetalle();
    	return lst;
	}

}
