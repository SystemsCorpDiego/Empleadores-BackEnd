package ar.ospim.empleadores.nuevo.infra.input.rest.app.mail.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailTipoConfiguracionDto {

	private Long id;
	private Integer mailId;
	private Integer diaProceso;
	private String cuerpoMail;
	private Boolean habilitado;

}
