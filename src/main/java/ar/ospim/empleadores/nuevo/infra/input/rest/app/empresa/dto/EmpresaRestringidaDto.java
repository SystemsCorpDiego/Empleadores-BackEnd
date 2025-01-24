package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmpresaRestringidaDto {
	private Integer id;
	private String observacion;
	private String cuit;
	private String razonSocial;   
}
