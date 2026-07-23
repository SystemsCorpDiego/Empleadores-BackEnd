package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "deuda_nomina_mail_envio")
@ToString
@Getter
@Setter
public class DeudaNominaMailEnvio {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")	
    private Long id;
	
	
	@Column(name = "fecha_proceso")
    private LocalDate fechaProceso;
	
	@Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

	@Column(name = "cuit")
    private String cuit;

	@Column(name = "datos")
    private String datos;

	@Column(name = "cuerpo_mail")
    private String cuerpoMail;
	
	@Column(name = "estado")
	private String estado;
}
