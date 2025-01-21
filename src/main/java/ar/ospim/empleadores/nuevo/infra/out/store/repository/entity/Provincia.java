package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "provincia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Provincia {
	
	@Id
	@Column(name = "id_provincia")
	private Integer id;
	
	private String detalle;

	@Column(name = "id_sssalud")
	private Integer sssaludId;
	
	@Column(name = "id_provincia_afip")
	private String afipId;
	
	@Column(name = "cod_postal")
	private String codigoPostal;
}
