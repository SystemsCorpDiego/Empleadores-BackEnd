package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import ar.ospim.empleadores.comun.auditable.entidad.SGXAuditableEntity;
import ar.ospim.empleadores.comun.auditable.listener.SGXAuditListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ddjj")
@EntityListeners(SGXAuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DDJJ extends SGXAuditableEntity<Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1605991800570365724L;

	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@Column(name = "periodo", nullable = false)
	private LocalDate periodo;
	
	@Column(name = "secuencia")
	private Integer secuencia;
	
	
	@OneToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	
	@Column(name = "estado", nullable = false)
	private String estado;
	
	@Column(name = "presentada_fecha")
	private LocalDate presentacion;
	 
	@JsonManagedReference
	@OneToMany(mappedBy = "ddjj", cascade = { CascadeType.MERGE})
	private List<DDJJEmpleado> empleados;

	@Transient
	List<DDJJEmpleado> lstEmpleadoBaja = new ArrayList<DDJJEmpleado>();
	@Transient
	List<DDJJEmpleado> lstEmpleadoAporteBaja= new ArrayList<DDJJEmpleado>();


}
