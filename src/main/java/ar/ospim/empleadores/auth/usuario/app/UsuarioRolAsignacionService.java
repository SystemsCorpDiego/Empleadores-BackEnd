package ar.ospim.empleadores.auth.usuario.app;

import java.util.List;

import ar.ospim.empleadores.nuevo.infra.out.store.enums.ERol;

public interface UsuarioRolAsignacionService {
	List<RolAsignado> getRolAsignado(Integer userId);

	void saveUsuarioRol(Integer userId, ERol role);
	
	void saveUsuarioRol(Integer usuarioId, Short idRol);
		
	void removePermisos(Integer userId);

}
