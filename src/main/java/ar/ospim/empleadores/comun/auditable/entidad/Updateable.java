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
public class Updateable implements Serializable {

	@Column(name = "actu_en")
	@ToString.Include
	private LocalDateTime updatedOn;

	@Column(name = "actu_por")
	@ToString.Include
	private Integer updatedBy;

}