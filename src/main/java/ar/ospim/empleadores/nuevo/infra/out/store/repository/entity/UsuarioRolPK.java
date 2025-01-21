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
/**
 * Para el caso de los roles donde ERole.isAdmin == true, la institución es -1.
 * Esos son roles de administración que no están asociados a ninguna institución.
 */
public class UsuarioRolPK implements Serializable {

	private static final long serialVersionUID = -5482098137325590681L;

	public static final Number UNDEFINED_ID = -1;

	@Column(name = "usuario_id", nullable = false)
	private Integer userId;

	@Column(name = "rol_id", nullable = false)
	private Short roleId;

	/*
	public UserRolePK(Integer userId, Short roleId) {
		this(userId, roleId);
	}
	 */
	
	public UsuarioRolPK(Integer userId, Short roleId) {
		this.userId = userId;
		this.roleId = roleId;		
	}
}
