package ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto;

import lombok.Data;

@Data
public class CambiarClaveTokenDto {

	private String token;
	private String clave;
	
}
