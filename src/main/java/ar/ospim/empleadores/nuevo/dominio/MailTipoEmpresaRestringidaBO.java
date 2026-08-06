package ar.ospim.empleadores.nuevo.dominio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailTipoEmpresaRestringidaBO {
	private Integer id;
	private Integer mailId;
	private String observacion;
	private String cuit;
	private String razonSocial;
}
