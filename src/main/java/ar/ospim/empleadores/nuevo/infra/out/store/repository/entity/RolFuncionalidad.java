package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "rol_funcionalidad")
@Getter
@Setter
@ToString
public class RolFuncionalidad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
	@Column(name = "rol", nullable = false)
	private String rol;

	@Column(name = "funcionalidad", nullable = false)
	private String funcionalidad;

	private boolean activo ;
}
