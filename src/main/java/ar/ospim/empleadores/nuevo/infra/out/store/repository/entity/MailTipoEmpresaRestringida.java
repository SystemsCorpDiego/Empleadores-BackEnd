package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mail_tipo_empresa_restringida")
@Getter
@Setter
public class MailTipoEmpresaRestringida {
	
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "mail_id")
    private Integer mailId;

	@Column(name = "observacion", length = 200)
	private String observacion;

	@Column(name = "cuit", length = 11)
	private String cuit;   
	
}
