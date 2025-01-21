package ar.ospim.empleadores.nuevo.app.servicios.camara;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.CamaraBO;
import ar.ospim.empleadores.nuevo.app.dominio.CamaraCategoriaBO;

public interface CamaraService {

	public List<CamaraBO> getAllCamara();
	public List<CamaraCategoriaBO> getAllCategoria();
	
	public Boolean validar(String codigo);
	public Boolean validarCategoria(String camara, String categoria);
	
}
