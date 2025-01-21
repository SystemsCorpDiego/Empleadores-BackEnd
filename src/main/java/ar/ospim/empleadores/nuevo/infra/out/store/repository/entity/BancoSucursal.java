package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "banco_sucursal")
@Getter
@Setter
@ToString
public class BancoSucursal {
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
    private String codigo;
    private String descripcion;

	@OneToOne
	@JoinColumn(name = "banco_id")
    private Banco banco;
}
