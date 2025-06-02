package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "convenio_ddjj_deuda_nomina")
@Getter
@Setter
public class ConvenioDdjjDeudaNomina {

	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@JsonIgnore 
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "convenio_ddjj_id")	
	private ConvenioDdjj convenioDdjj;
	
 
	
	@Column(name = "boleta_id")
	private Integer boletaId;

	@Column(name = "aporte")
	private String aporte;
	
	@Column(name = "aporte_importe")
	private BigDecimal aporteImporte;

	@Column(name = "vencimiento")
	private LocalDate vencimiento;
	
	@Column(name = "interes")
	private BigDecimal interes;

	@Override
	public String toString() {
		return "ConvenioDdjjDeudaNomina [id=" + id + ", boletaId=" + boletaId + ", aporte=" + aporte
				+ ", aporteImporte=" + aporteImporte + ", vencimiento=" + vencimiento + ", interes=" + interes + "]";
	}
	
	
}
