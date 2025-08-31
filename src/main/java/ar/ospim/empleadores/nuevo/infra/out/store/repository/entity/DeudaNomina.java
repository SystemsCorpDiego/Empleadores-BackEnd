package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "deuda_nomina")
@ToString
@Getter
@Setter
public class DeudaNomina {

	@Id 
	@Column(name = "id")
    private Integer id;

	@Column(name = "entidad")
    private String entidad;
	
	@Column(name = "cuit")
    private String cuit;
	
	@Column(name = "periodo")
    private LocalDate periodo;
	
	@OneToOne
	@JoinColumn(name = "aporte")
	private Aporte aporte;

	
	@Column(name = "ddjj_id")
    private Integer ddjjId;
	
	@Column(name = "boleta_id")
    private Integer boletaId;
	

	@Column(name = "fecha_info")
    private LocalDate fechaInfor;
	
	
	@Column(name = "aporte_importe")
    private BigDecimal importe;

	@Column(name = "interes")
    private BigDecimal interes;

	@Column(name = "vencimiento")
    private LocalDate vencimiento;
	 
	@Column(name = "interes_fecha_info")
    private LocalDate interesFechaInfo;
	
	@Column(name = "aporte_pago")
    private BigDecimal aportePago;

	@Column(name = "aporte_pago_fecha_info")
    private LocalDate aportePagoFechaInfo;
	
	@Column(name = "acta_id")
    private Long actaId;
	
	@Column(name = "acta_fecha_info")
    private LocalDate actaFechaInfo;

	@Column(name = "convenio_id")
    private Long convenioId;
    
}
