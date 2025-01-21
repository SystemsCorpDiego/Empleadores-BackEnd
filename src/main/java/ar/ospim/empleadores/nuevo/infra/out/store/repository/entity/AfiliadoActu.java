package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "afiliado_actu")
@ToString
@Getter
@Setter
public class AfiliadoActu {
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@JoinColumn(name = "cuil_titular")	
	private String cuil_titular;
	
	@JoinColumn(name = "apellido")
	private String apellido;
	
	@JoinColumn(name = "nombre")
	private String nombre;
	
	@JoinColumn(name = "empresa_id")
	private Integer empresa_id;
	
	@JoinColumn(name = "creado_por")
	private Integer creado_por;
}
