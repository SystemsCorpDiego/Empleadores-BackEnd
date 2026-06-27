package ar.ospim.empleadores.nuevo.app.dominio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmpresaRestringidaMailBO {
	private Integer id;
	private String observacion;
	private String cuit;
	private String razonSocial;
}
