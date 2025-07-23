package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "convenio_ddjj")
@Getter
@Setter
public class ConvenioDdjj {
	
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@JsonIgnore 
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "convenio_id")	
	private Convenio convenio;
	
	@JsonIgnore 
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ddjj_id")	
	private DDJJ ddjj;
	

	@JsonManagedReference	
	@OneToMany(mappedBy = "convenioDdjj", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE})
	private List<ConvenioDdjjDeudaNomina> ddjjDeudaNomina;


	@Override
	public String toString() {
		return "ConvenioDdjj [id=" + id + ", ddjj=" + ddjj + ", ddjjDeudaNomina=" + ddjjDeudaNomina + "]";
	}

	
}
