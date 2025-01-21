package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class BoletaPagoAjusteId implements Serializable  {
	private static final long serialVersionUID = 1702490547488341060L;
	
	@Column(name = "boleta_pago_id")
	private Integer boletaPagoId;
	
	@Column(name = "ajuste_id")
    private Integer ajusteId;
}
