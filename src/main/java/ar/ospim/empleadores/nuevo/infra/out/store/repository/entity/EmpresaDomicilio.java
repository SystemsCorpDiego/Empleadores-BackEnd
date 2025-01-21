package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.io.Serializable;

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

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "empresa_domicilio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmpresaDomicilio implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1438976952576195254L;

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name = "empresa_id")
	private Integer empresaId;	
	
	@Column(name = "tipo")
	private String tipo;

	
	@JsonManagedReference
	@ManyToOne
	@JoinColumns({
        @JoinColumn(name="provincia_id", referencedColumnName="id_provincia")        
    })
	private Provincia provincia;
	
	@JsonManagedReference
	@ManyToOne
	@JoinColumns({
        @JoinColumn(name="localidad_id", referencedColumnName="id_localidad")        
    })
	private Localidad localidad;
	
	@Column(name = "calle", length = 200)
	private String calle;
	@Column(name = "calle_nro", length = 50)
	private String calleNro;
	@Column(name = "piso", length = 50)
	private String piso;
	@Column(name = "depto", length = 50)
	private String depto;
	@Column(name = "oficina", length = 50)
	private String oficina;
	@Column(name = "cp", length = 50)
	private String cp;
	@Column(name = "planta", length = 50)
	private String planta;
	
}
