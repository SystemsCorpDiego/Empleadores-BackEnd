package ar.ospim.empleadores.nuevo.app.dominio;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LocalidadBO {
	private Integer id;
	private String descripcion;
	private ProvinciaBO provincia;
}
