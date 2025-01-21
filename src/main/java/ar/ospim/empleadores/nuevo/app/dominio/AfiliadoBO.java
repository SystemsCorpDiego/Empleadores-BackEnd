package ar.ospim.empleadores.nuevo.app.dominio;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AfiliadoBO {
	private String cuil;	
	private Integer inte;
	@EqualsAndHashCode.Exclude
	private String apellido;
	@EqualsAndHashCode.Exclude
	private String nombre;	 
}
