package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import ar.ospim.empleadores.comun.auditable.entidad.SGXAuditableEntity;
import ar.ospim.empleadores.comun.auditable.listener.SGXAuditListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuario_clave")
@EntityListeners(SGXAuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper=true)
public class UsuarioClave extends SGXAuditableEntity<Integer>  {
	
	private static final long serialVersionUID = -4190168188433565967L;

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "clave", nullable = false)
	private String clave;

	@Column(name = "salt", nullable = false)
	private String salt;

	@Column(name = "hash_algorithm", nullable = false)
	private String hashAlgorithm;

}
