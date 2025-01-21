package ar.ospim.empleadores.nuevo.app.servicios.localidad;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.LocalidadBO;
import ar.ospim.empleadores.nuevo.infra.out.store.LocalidadStorage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LocalidadServiceImpl implements LocalidadService {
	
	private final LocalidadStorage localidadStorage;
	
	@Override
	public List<LocalidadBO> consulta(Integer provinciaId) {
		List<LocalidadBO> lst = null;
		if ( provinciaId == null ) {
			lst = localidadStorage.findAll();
		} else {
			lst = localidadStorage.findByProvinciaId(provinciaId);
		}
    	return lst;
	}

	@Override
	public List<LocalidadBO> consultaOrderByDescripcion(Integer provinciaId){
		List<LocalidadBO> lst = null;
		if ( provinciaId == null ) {
			lst = localidadStorage.findAllOrderByDescripcion();
		} else {
			lst = localidadStorage.findByProvinciaIdOrderByDescripcion(provinciaId);
		}
    	return lst;
	}
}
