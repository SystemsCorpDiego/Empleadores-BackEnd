package ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter 
@ToString
public class UsuarioInternoAltaDto extends UsuarioInternoDto {
	private String clave;
}
