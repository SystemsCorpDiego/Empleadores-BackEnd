package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ddjj_deta_aporte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DDJJEmpleadoAporte {
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
		
	@OneToOne
	@JoinColumn(name = "aporte")
	private Aporte aporte;

	@Column(name = "importe", nullable = false)
	private BigDecimal importe;
	
	@JsonIgnore 
	@JsonBackReference
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "ddjj_deta_id", referencedColumnName = "id") })
	private DDJJEmpleado ddjjEmpleado;

	@Override
	public String toString() {
		return "DDJJEmpleadoAporte [id=" + id + ", aporte=" + aporte + ", importe=" + importe + "]";
	}

	
}
