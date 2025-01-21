package ar.ospim.empleadores.nuevo.app.servicios.localidad;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.LocalidadBO;

public interface LocalidadService {

	public List<LocalidadBO> consulta(Integer provinciaId);
	public List<LocalidadBO> consultaOrderByDescripcion(Integer provinciaId);

}
