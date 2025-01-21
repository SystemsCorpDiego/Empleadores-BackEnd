package ar.ospim.empleadores.nuevo.infra.input.rest.app.aporte.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AporteDto {

	private String codigo;
	private String descripcion;
	private String cuenta;
	private String entidad;
	private Integer orden;
	
	
}
