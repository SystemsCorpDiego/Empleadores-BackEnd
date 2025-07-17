package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "convenio_cuota")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConvenioCuota {
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@JsonIgnore 
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "convenio_id")	
	private Convenio convenio;
	
	@Column(name = "cuota_nro")
	private Integer cuotaNro;
	 
	@Column(name = "vencimiento")
	private LocalDate vencimiento;
	
	@Column(name = "importe")
	private BigDecimal importe;
	
	@Column(name = "interes")
	private BigDecimal interes;

	@Override
	public String toString() {
		return "ConvenioCuota [id=" + id + ", cuotaNro=" + cuotaNro + ", vencimiento=" + vencimiento+ ", interes=" + interes + ", importe="
				+ importe + "]";
	}	  
	 
}
