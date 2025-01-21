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
@Table(name = "aporte_vencimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AporteVencimiento {
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "aporte")
	private String aporteCodigo;

	private LocalDate desde;
	private LocalDate hasta;
	
	private Integer dia;
}
