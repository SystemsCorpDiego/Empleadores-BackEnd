package ar.ospim.empleadores.nuevo.app.dominio;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaRestringidaBO {
	private Integer id;
	private String observacion;
	private String cuit;
	private String razonSocial;
}
