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
@Table(name = "convenio_periodo_detalle")

@Getter
@Setter
public class ConvenioPeriodoDetalle {

	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@JsonIgnore 
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "convenio_id")	
	private Convenio convenio;

	private LocalDate periodo;
	private String aporte;
	@Column(name = "aporte_importe")
	private BigDecimal importe;
	private BigDecimal interes;
	
	@Column(name = "deuda_nomina_id")
	private Integer deudaNominaId;
	@Column(name = "ddjj_id")
	private Integer ddjjId;
	@Column(name = "boleta_id")
	private Integer boletaId;

}
