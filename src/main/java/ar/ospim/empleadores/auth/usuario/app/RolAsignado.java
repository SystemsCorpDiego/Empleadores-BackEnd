package ar.ospim.empleadores.auth.usuario.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class RolAsignado  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8961180829267743817L;

	private Short id;
	private String descripcion;

	
	//public final ERol rol;

	/*
	public RolAsignado(ERol rol) {
		this.rol = rol;
	}

	public RolAsignado(Short rolId) {
		this(ERol.map(rolId));
	}

	public boolean isRol(ERol aRol) {
		return this.rol.equals(aRol);
	}

	public boolean isAssigment(ERol aRol) {
		return isRol(aRol);
	}
	*/
}
