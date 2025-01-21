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
@Table(name = "afip_vencimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AfipVencimiento {
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	private LocalDate vigencia;
	
	private Integer dia;
	
}
