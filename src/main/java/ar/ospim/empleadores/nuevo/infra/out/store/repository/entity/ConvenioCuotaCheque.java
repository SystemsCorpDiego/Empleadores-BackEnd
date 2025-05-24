package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "convenio_cuota_cheque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConvenioCuotaCheque {
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@JsonIgnore 
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "convenio_cuota_id")	
	private ConvenioCuota convenioCuota;
	
	
	@Column(name = "cheque_nro")
	private String numero; 
	
	private LocalDate fecha;
	private BigDecimal importe;

}
