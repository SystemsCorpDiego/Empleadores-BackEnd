package ar.ospim.empleadores.nuevo.app.dominio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LocalidadBO {
	private Integer id;
	private String descripcion;
	private ProvinciaBO provincia;
}
