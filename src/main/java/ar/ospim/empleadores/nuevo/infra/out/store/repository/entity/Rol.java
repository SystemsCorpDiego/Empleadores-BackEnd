package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ar.ospim.empleadores.comun.auditable.entidad.SGXAuditableEntity;
import ar.ospim.empleadores.comun.auditable.listener.SGXAuditListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "rol")
@EntityListeners(SGXAuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Rol extends SGXAuditableEntity<Short> {
	
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short id;
	
	@Column(name = "descripcion", length = 200)
	private String descripcion;

	public Rol(Short id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}
	
	
}
