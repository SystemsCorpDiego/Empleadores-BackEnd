package ar.ospim.empleadores.nuevo.app.servicios.usuario;

import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;

public interface UsuarioMailGet {

	public String run(Integer usuarioId);
	public String run(UsuarioBO usuario);
	
}
