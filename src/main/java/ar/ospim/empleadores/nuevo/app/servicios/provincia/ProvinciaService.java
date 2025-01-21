package ar.ospim.empleadores.nuevo.app.servicios.provincia;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.ProvinciaBO;

public interface ProvinciaService {
	public List<ProvinciaBO> consulta();
	public List<ProvinciaBO> consultaOrderByDetalle();
}
