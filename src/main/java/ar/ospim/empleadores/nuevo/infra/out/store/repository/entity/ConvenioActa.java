package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
@Table(name = "convenio_acta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConvenioActa {

	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@JsonIgnore 
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "convenio_id")	
	private Convenio convenio;
	
	@JsonManagedReference
	@ManyToOne	
	@JoinColumns( {
        @JoinColumn(name = "acta_id", referencedColumnName = "id")        
    })
	private ActaMolineros acta;

	@Override
	public String toString() {
		return "ConvenioActa [id=" + id + ", acta=" + acta + "]";
	}
	  
	
}
