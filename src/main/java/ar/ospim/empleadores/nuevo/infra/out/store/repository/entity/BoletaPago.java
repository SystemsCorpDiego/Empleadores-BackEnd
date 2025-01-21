package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "boleta_pago")
public class BoletaPago {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

	@OneToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	
	@JoinColumn(name = "secuencia")
	private Integer secuencia;

	@OneToOne
	@JoinColumn(name = "aporte")
	private Aporte aporte;
	
	@Column(name = "importe")
	private BigDecimal importe;
	
	@Column(name = "intencion_pago")
	private LocalDate intencionDePago;
	
	private LocalDate vencimiento;
	
	@Column(name = "interes")
	private BigDecimal interes;

	@Column(name = "ddjj_id")
	private Integer ddjjId;

	@Column(name = "acta_nro")
	private String actaNro;

	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "forma_pago")
	private String formaDePago;
	
	@Column(name = "bep")
	private String bep;
	
	@Column(name = "impresion")
	private LocalDate impresion;
	
	@Column(name = "baja_en")
	private LocalDate baja;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "boletaPago", cascade = { CascadeType.MERGE})
	private List<BoletaPagoAjuste> ajustes;  

}
