package ar.ospim.empleadores.nuevo.app.dominio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PersonaBO {
	private Integer id;
	private String nombre;
	private String apellido;
	private String email;
	private Boolean notificaciones;
}
