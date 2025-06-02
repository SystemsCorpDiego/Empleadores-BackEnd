package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "convenio_ajuste")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConvenioAjuste {
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@Column(name = "importe")
	private BigDecimal importe; //-- importe usado del ajuste seleccionado.-
	
	//convenio_id integer NOT NULL,
	@JsonIgnore 
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "convenio_id")	
	private Convenio convenio;
	
	//  ajuste_id integer NOT NULL, 
	@OneToOne
	@JoinColumn(name = "ajuste_id")
	private Ajuste ajuste;

	@Override
	public String toString() {
		return "ConvenioAjuste [id=" + id + ", importe=" + importe + ", ajuste=" + ajuste + "]";
	}	   
	  
	
}
