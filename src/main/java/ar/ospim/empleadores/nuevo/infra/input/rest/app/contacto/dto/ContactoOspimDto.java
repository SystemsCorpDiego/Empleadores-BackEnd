package ar.ospim.empleadores.nuevo.infra.input.rest.app.contacto.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ContactoOspimDto {
	private String email;
	private String telefono;
	private String whasap;
	private String diasHorarios;
}
