package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "ajuste")
@NamedStoredProcedureQuery(
			name = "Ajuste.generarAjusteAutomaticoIPF", 
			procedureName = "generarAjusteAutomaticoIPF",  
			parameters = { @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_aporte", type = String.class),
								    @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_empresa_id", type = Integer.class)}
)
public class Ajuste {
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id")	
	private Empresa empresa;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "aporte")	
	private Aporte aporte;
	
	private LocalDate periodo;
	private BigDecimal importe;
	private LocalDate vigencia;
	
	private String motivo;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "ajuste")
    private List<BoletaPagoAjuste> boletaPagoAjuste;
	
}
