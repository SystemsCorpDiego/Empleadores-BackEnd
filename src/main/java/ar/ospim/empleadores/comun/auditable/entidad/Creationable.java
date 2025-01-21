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
public class Creationable implements Serializable {

	@Column(name = "creado_en")
	@ToString.Include
	private LocalDateTime createdOn;

	@Column(name = "creado_por")
	@ToString.Include
	private Integer createdBy;

}
