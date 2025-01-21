package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "v_escala_salarial")
@Immutable
@Setter
@Getter
@ToString
public class VEscalaSalarial {
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "tipo")
	private String tipo;

	@Column(name = "camara")
	private String camara;
	
	@Column(name = "categoria")
	private String categoria;
	
	@Column(name = "antiguedad")
	private Integer antiguedad;
	
	@Column(name = "vigencia")
	private LocalDate vigencia;

	@Column(name = "basico")
	private BigDecimal basico;
}
