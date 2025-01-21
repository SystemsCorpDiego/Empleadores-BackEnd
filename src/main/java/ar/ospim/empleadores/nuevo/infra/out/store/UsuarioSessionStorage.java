package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.stream.Stream;

import ar.ospim.empleadores.auth.usuario.app.RolAsignado;

public interface UsuarioSessionStorage {
	/**
	 * Retorna el ID del usuario que hizo el request
	 */
	Integer getUserId();

	/**
	 * Retorna los roles usuario que hizo el request
	 */
	Stream<RolAsignado> getRolesAssigned();
}
