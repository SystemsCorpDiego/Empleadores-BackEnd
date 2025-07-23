package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ddjj_deta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DDJJEmpleado {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
	
	@JsonIgnore 
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ddjj_id")	
	private DDJJ ddjj;

	@JsonManagedReference
	@ManyToOne	
	@JoinColumns( {
        @JoinColumn(name = "afiliado_cuil_titular", referencedColumnName = "cuil_titular"),
        @JoinColumn(name = "afiliado_inte", referencedColumnName = "inte")
    })
	private Afiliado afiliado;
	
	@ManyToOne
	@JoinColumn(name = "empresa_domicilio_id")
	private EmpresaDomicilio empresaDomicilio;
	
	
	@Column(name = "camara")
    private String  camara;
    
	@Column(name = "categoria", nullable = false)
    private String  categoria;
    
	@Column(name = "remunerativo", nullable = false)
    private BigDecimal remunerativo;
    
	@Column(name = "no_remunerativo", nullable = false)
    private BigDecimal noRemunerativo;
    
	@Column(name = "ingreso")
    private LocalDate ingreso;    
	
	@Column(name = "uoma_socio")
    private Boolean uomaSocio;    
	
	@Column(name = "antima_socio")
    private Boolean amtimaSocio;    
	
	@JsonManagedReference
	@OneToMany(mappedBy = "ddjjEmpleado", cascade = { CascadeType.MERGE })
	private List<DDJJEmpleadoAporte> aportes;

	@Override
	public String toString() {
		return "DDJJEmpleado [id=" + id + ", afiliado=" + afiliado + ", empresaDomicilio=" + empresaDomicilio
				+ ", camara=" + camara + ", categoria=" + categoria + ", remunerativo=" + remunerativo
				+ ", noRemunerativo=" + noRemunerativo + ", ingreso=" + ingreso + ", uomaSocio=" + uomaSocio
				+ ", amtimaSocio=" + amtimaSocio + ", aportes=" + aportes + "]";
	}  

	
}
