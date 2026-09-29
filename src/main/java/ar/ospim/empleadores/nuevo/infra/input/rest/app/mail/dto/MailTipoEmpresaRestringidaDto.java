package ar.ospim.empleadores.nuevo.infra.input.rest.app.mail.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailTipoEmpresaRestringidaDto {
	private Integer id;
	private Integer mailId;
	private String observacion;
	private String cuit;
	private String razonSocial;
}
