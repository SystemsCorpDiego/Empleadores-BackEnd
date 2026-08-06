package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

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
@Table(name = "mail_tipo_config")
@ToString
@Getter
@Setter
public class MailTipoConfiguracion {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")	
    private Integer id;
	
	@Column(name = "mail_id")
    private Integer mailId;
	
	@Column(name = "dia_proceso")
    private Integer diaProceso;
	
	@Column(name = "cuerpo_mail")
    private String cuerpoMail;
	
	
	private Boolean habilitado;
	
}
