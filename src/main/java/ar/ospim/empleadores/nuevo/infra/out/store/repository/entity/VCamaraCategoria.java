package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "v_camara_categoria")
@Immutable
@Setter
@Getter
@ToString
public class VCamaraCategoria implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7293973356108030426L;

	@EmbeddedId
    private VCamaraCategoriaPK pk;
	
	 
}
