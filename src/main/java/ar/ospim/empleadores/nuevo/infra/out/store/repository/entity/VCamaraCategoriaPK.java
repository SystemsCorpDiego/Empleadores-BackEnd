package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VCamaraCategoriaPK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7293973356108030426L;

	@Column(name = "camara", nullable = false)
	private String camara;
	
	@Column(name = "categoria", nullable = false)
	private String categoria;
}
