package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "localidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Localidad {

	@Id
	@Column(name="id_localidad")
	private Integer id;
	
	@JsonManagedReference
	@ManyToOne
	@JoinColumns({
        @JoinColumn(name="id_provincia", referencedColumnName="id_provincia")        
    })
	private Provincia provincia;
	
	@Column(name="detalle")
	private String detalle;
	
	@Column(name="id_provinciasss")
	private Integer  sssProvinciaId;
	
	@Column(name="id_localidadesss")
	private Integer  sssLocalidadId ;
	  
	@Column(name="cod_postal")
	private String codigoPostal;
}
