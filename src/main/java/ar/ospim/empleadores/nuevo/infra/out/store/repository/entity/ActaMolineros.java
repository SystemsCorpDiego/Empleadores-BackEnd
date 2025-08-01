package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "acta_molineros")
@ToString
@Getter
@Setter
public class ActaMolineros {
	
	@Id 
	@Column(name = "id")
    private Integer id;
	@Column(name = "numero")
    private String numero;
	
	@Column(name = "entidad")
    private String entidad;
	@Column(name = "cuit")
    private String cuit ;
	@Column(name = "estado")
    private String estado;
	@Column(name = "fecha")
    private LocalDate fecha;	
	
	@Column(name = "capital")
    private BigDecimal capital;
	@Column(name = "interes")
    private BigDecimal interes;	
	
	@Column(name = "convenio_id")
    private Integer convenio_id;
	
	@Column(name = "otros")
    private BigDecimal otros;
    
	@Column(name = "pago")
    private BigDecimal pago;
	
}