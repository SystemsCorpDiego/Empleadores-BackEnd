package ar.ospim.empleadores.comun.auditable.listener;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ar.ospim.empleadores.comun.auditable.entidad.SGXAuditableEntity;
import ar.ospim.empleadores.comun.auth.usuario.SecurityContextUtils;

public class SGXAuditListener {

	@PrePersist
	public void setCreationable(SGXAuditableEntity auditable) {
		auditable.setCreatedOn(LocalDateTime.now());
		auditable.setUpdatedOn(LocalDateTime.now());
		auditable.setCreatedBy(getCurrentAuditor());
		auditable.setUpdatedBy(getCurrentAuditor());
		auditable.setDeleted(false);
	}

	@PreUpdate
	public void setUpdateable(SGXAuditableEntity auditable) {
		auditable.setUpdatedOn(LocalDateTime.now());
		auditable.setUpdatedBy(getCurrentAuditor());
	}


	public Integer getCurrentAuditor() {
		return SecurityContextUtils.getUserDetails().userId;
	}
}
