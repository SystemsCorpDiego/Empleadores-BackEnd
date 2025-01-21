package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;

import ar.ospim.empleadores.auth.usuario.app.RolAsignado;

public interface UsuarioSesionStorage {
	/**
	 * Retorna el ID del usuario que hizo el request
	 */
	Integer getUsuarioId();

	/**
	 * Retorna los roles usuario que hizo el request
	 */
	List<RolAsignado> getRolesAsignados();

}
