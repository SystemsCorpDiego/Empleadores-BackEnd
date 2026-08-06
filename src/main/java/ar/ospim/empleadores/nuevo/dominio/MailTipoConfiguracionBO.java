package ar.ospim.empleadores.nuevo.dominio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailTipoConfiguracionBO {

	private Integer id;
	private Integer mailId;
	private Integer diaProceso;
	private String cuerpoMail;
	private Boolean habilitado;
	
}
