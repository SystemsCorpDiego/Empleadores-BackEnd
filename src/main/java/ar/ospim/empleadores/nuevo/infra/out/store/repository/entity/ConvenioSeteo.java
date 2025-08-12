package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.math.BigDecimal;
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


@Entity
@Table(name = "convenio_seteo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConvenioSeteo {

	@Id 
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name = "cuit")
	private String cuit;
	
	@Column(name = "cuotas")
	private Integer cuotas;
	
	@Column(name = "antiguedad")
	private Integer antiguedad;
	
	@Column(name = "intencion_pago_dias_max")
	private Integer intencionPagoDiasMax;
	
	@Column(name = "tasa")
	private BigDecimal tasa;
	
	@Column(name = "forma_pago_ventanilla")
	private Boolean formaPagoVentanilla;
	
	@Column(name = "forma_pago_redlink")
	private Boolean formaPagoRedlink;
	
	@Column(name = "forma_pago_banelco")
	private Boolean formaPagoBanelco;
	
	@Column(name = "forma_pago_cheque")
	private Boolean formaPagoCheque;
	
	@Column(name = "desde")
	private LocalDate desde;
	
	@Column(name = "hasta")
	private LocalDate hasta;

}
