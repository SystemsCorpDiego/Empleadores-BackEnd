package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "v_camara")
@Immutable
@Setter
@Getter
@ToString
public class VCamara {
	
	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "codigo")
	private String codigo; 
	
	@Column(name = "descripcion")
	private String descripcion;
}
