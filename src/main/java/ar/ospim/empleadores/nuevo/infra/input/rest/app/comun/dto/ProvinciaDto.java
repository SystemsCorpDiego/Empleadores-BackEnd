package ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProvinciaDto {
	private Integer id;
	private String descripcion;
}
