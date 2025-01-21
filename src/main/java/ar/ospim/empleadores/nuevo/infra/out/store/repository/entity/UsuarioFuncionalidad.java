package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import ar.ospim.empleadores.comun.auditable.entidad.SGXAuditableEntity;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "usuario_funcionalidad")
@EqualsAndHashCode
public class UsuarioFuncionalidad extends SGXAuditableEntity<UsuarioFuncionalidadPK> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2826874188803670205L;

	@EmbeddedId
	public UsuarioFuncionalidadPK usuarioFuncionalidadPK;

	public UsuarioFuncionalidad() {

	}

	public UsuarioFuncionalidad(Integer userId, String funcionalidadCodigo) {
		usuarioFuncionalidadPK = new UsuarioFuncionalidadPK(userId, funcionalidadCodigo);
		setDeleted(false);
	}
	
	public void setUserId(Integer userId) {
		if (this.usuarioFuncionalidadPK == null)
			this.usuarioFuncionalidadPK = new UsuarioFuncionalidadPK();
		this.usuarioFuncionalidadPK.setUsuarioId(userId);
	}

	public void setRoleId(String funcionalidadCodigo) {
		if (this.usuarioFuncionalidadPK == null)
			this.usuarioFuncionalidadPK = new UsuarioFuncionalidadPK();
		this.usuarioFuncionalidadPK.setFuncionalidadCodigo(funcionalidadCodigo);
	}
	
	
	public UsuarioFuncionalidadPK getUserRolePK() {
		return usuarioFuncionalidadPK;
	}

	public Integer getUsuarioId() {
		return usuarioFuncionalidadPK.getUsuarioId();
	}
	
	public String getFuncionalidadCodigo() {
		return usuarioFuncionalidadPK.getFuncionalidadCodigo();
	}

	
	@Override
	public UsuarioFuncionalidadPK getId() {
		return this.usuarioFuncionalidadPK;
	}
}
