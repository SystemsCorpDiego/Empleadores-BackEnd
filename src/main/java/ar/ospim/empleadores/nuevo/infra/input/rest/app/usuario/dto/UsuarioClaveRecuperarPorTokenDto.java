package ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario.dto;

import lombok.Data;

@Data
public class UsuarioClaveRecuperarPorTokenDto {
	
	private String usuario;
	private String email;	
	
}
