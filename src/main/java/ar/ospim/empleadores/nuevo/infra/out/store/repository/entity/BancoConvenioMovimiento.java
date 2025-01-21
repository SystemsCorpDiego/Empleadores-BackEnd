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
@Table(name = "banco_convenio_mov_tipo")
@Getter
@Setter
@ToString
public class BancoConvenioMovimiento {
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@OneToOne
	@JoinColumn(name = "convenio_id")
	private BancoConvenio convenio;
	
    private String tipo;
    private String descripcion;

	@OneToOne
	@JoinColumn(name = "aporte")
    private Aporte aporte;

}
