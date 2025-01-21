package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.ToString;

@Entity
@Table(name = "sugerenciadatosafiliado")
@ToString
public class SugerenciaDatosAfiliado {
	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne
	private Empresa empresa;

	private String apellido;
	private String nombre;

}
