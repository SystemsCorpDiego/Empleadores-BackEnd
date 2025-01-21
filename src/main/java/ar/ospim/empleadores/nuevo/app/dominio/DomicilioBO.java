package ar.ospim.empleadores.nuevo.app.dominio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DomicilioBO {
	private Integer id;
	
	private ProvinciaBO provincia;
	
	private LocalidadBO localidad;
	
	private String calle;
	private String calleNro;
	private String piso;
	private String depto;
	private String cp;
	
}
