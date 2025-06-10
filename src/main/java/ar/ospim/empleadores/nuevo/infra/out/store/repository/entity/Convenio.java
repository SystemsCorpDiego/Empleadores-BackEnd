package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "convenio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Convenio {

	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name = "entidad")
	private String entidad; //AMTIMA, OSPIM, UOMA
	
	@Column(name = "estado")
	private String estado; //--PENDIENTE, CERRADO, CHEQUE RECIBIDO
	
	@OneToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;

	  //fecha_alta timestamp without time zone NOT NULL, -- demas auditoria
	
	@Column(name = "imp_deuda")
	private BigDecimal importeDeuda;
	
	@Column(name = "imp_interes")
	private BigDecimal importeIntereses;
	
	@Column(name = "imp_saldo_favor")
	private BigDecimal importeSaldoFavor;
	
	@Column(name = "intencion_pago")
	private LocalDate intencionDePago;
	
	@Column(name = "cuotas_cantidad")
	private Integer cuotasCanti;
	
	@Column(name = "medio_pago")
	private String medioPago;   

	@Column(name = "acta_id_ddjj")
	private Integer actaIdDDJJ;
	
	@Column(name = "convenio_id")
	private Integer convenioIdMolineros; //--Id convenio generado en Molineros
	
	@Column(name = "convenio_numero")
	private String convenioNumeroMolineros; //--Id convenio generado en Molineros
		
	
	@JsonManagedReference
	@OneToMany(mappedBy = "convenio", cascade = { CascadeType.MERGE})
	private List<ConvenioActa> actas;

	@JsonManagedReference
	@OneToMany(mappedBy = "convenio", cascade = { CascadeType.MERGE})
	private List<ConvenioDdjj> ddjjs;

	
	@JsonManagedReference
	@OneToMany(mappedBy = "convenio", cascade = { CascadeType.MERGE})
	private List<ConvenioAjuste> ajustes;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "convenio", cascade = { CascadeType.MERGE})
	private List<ConvenioCuota> cuotas;
	
	@Column(name = "fecha_alta")
	@ToString.Include
	private LocalDateTime createdOn;

	@Override
	public String toString() {
		return "Convenio [id=" + id + ", entidad=" + entidad + ", estado=" + estado + ", empresa=" + empresa
				+ ", importeDeuda=" + importeDeuda + ", importeIntereses=" + importeIntereses + ", importeSaldoFavor="
				+ importeSaldoFavor + ", intencionDePago=" + intencionDePago + ", cuotasCanti=" + cuotasCanti
				+ ", medioPago=" + medioPago + ", actaIdDDJJ=" + actaIdDDJJ + ", convenioIdMolineros="
				+ convenioIdMolineros + ", convenioNumeroMolineros=" + convenioNumeroMolineros + ", actas=" + actas
				+ ", ddjjs=" + ddjjs + ", ajustes=" + ajustes + ", cuotas=" + cuotas + ", createdOn=" + createdOn + "]";
	}

	
}
