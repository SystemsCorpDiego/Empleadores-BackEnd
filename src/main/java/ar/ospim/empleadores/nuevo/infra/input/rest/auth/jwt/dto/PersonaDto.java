package ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaDto {
	private String apellido;
	private String nombre;
	private String email;
}
