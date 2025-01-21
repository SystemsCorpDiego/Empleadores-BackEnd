package ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt.dto;

import lombok.Data;

@Data
public class EmpresaDto {
	private Integer id;
	private String cuit;
	private String razonSocial;	
	private boolean actividad_molinera; 
}
