package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class RolFuncionalidadPK implements Serializable {

	private static final long serialVersionUID = -5482098137325590681L;

	public static final Number UNDEFINED_ID = -1;

	@Column(name = "rol", nullable = false)
	private String rol;

	@Column(name = "funcionalidad", nullable = false)
	private String funcionalidad;
 
	public RolFuncionalidadPK(String rol, String funcionalidad) {
		this.rol = rol;
		this.funcionalidad = funcionalidad;		
	}
}
