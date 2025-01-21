package ar.ospim.empleadores.comun.auditable.entidad;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
public class Deleteable implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1845551268050864290L;

	@Column(name = "baja", nullable = false)
	@ToString.Include
	private Boolean deleted = false;

	@Column(name = "baja_en")
	@ToString.Include
	private LocalDateTime deletedOn;

	@Column(name = "baja_por")
	@ToString.Include
	private Integer deletedBy;

	public boolean isDeleted() {
		return deleted;
	}

}
