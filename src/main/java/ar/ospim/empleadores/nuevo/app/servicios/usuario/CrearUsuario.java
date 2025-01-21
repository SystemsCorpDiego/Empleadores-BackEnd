package ar.ospim.empleadores.nuevo.app.servicios.usuario;

import ar.ospim.empleadores.nuevo.app.dominio.RolBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;

public interface CrearUsuario {

	UsuarioBO run(String usuario, String email, String clave, RolBO rol);

	public UsuarioBO run(UsuarioBO usuario);
	
}
