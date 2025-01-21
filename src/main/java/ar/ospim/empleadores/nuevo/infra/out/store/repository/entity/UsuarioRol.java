package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import ar.ospim.empleadores.comun.auditable.entidad.SGXAuditableEntity;
import ar.ospim.empleadores.comun.auditable.listener.SGXAuditListener;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "usuario_rol")
@EntityListeners(SGXAuditListener.class)
@EqualsAndHashCode
public class UsuarioRol extends SGXAuditableEntity<UsuarioRolPK> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2826874188803670205L;

	@EmbeddedId
	public UsuarioRolPK userRolePK;

	public UsuarioRol() {

	}

	public UsuarioRol(Integer userId, Short roleId) {
		userRolePK = new UsuarioRolPK(userId, roleId);
		setDeleted(false);
	}
	
	public void setUserId(Integer userId) {
		if (this.userRolePK == null)
			this.userRolePK = new UsuarioRolPK();
		this.userRolePK.setUserId(userId);
	}

	public void setRoleId(Short roleId) {
		if (this.userRolePK == null)
			this.userRolePK = new UsuarioRolPK();
		this.userRolePK.setRoleId(roleId);
	}
	
	
	public UsuarioRolPK getUserRolePK() {
		return userRolePK;
	}

	public Integer getUserId() {
		return userRolePK.getUserId();
	}
	
	public Short getRoleId() {
		return userRolePK.getRoleId();
	}

	
	@Override
	public UsuarioRolPK getId() {
		return this.userRolePK;
	}
}
