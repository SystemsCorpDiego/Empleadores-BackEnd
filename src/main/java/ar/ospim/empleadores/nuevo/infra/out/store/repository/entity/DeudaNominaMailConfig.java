package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.time.LocalDate;

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
@Table(name = "deuda_nomina_mail_config")
@ToString
@Getter
@Setter
public class DeudaNominaMailConfig {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")	
    private Long id;
	
	
	@Column(name = "fecha_proceso")
    private LocalDate fechaEnvio;
	
	@Column(name = "cuerpo_mail")
    private String cuerpoMail;
	
}
