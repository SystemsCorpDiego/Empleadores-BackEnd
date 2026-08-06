package ar.ospim.empleadores.nuevo.app.servicios.entidad;

import java.util.List;

import ar.ospim.empleadores.nuevo.dominio.EntidadBO;

public interface EntidadService {
	public List<EntidadBO> consultar();
	public boolean validarCodigo(String codido);
}
