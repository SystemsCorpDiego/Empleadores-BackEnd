package ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LocalidadDto {
	private Integer id;
	private String descripcion;
	private Integer provinciaId;
}
