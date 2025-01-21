package ar.ospim.empleadores.nuevo.infra.input.rest.app.publicaciones.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PublicacionVigenteDto {
	private Integer id;
	private String titulo;
	private String cuerpo;

}
