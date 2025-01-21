package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "publicacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Publicacion {
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name = "titulo", length = 150)
    private String titulo;
	
	@Column(name = "cuerpo", length = 500)
    private String cuerpo;
	
	@Column(name = "vigencia_desde")
	private LocalDate vigenciaDesde;   

	@Column(name = "vigencia_hasta")
	private LocalDate vigenciaHasta;   

}
