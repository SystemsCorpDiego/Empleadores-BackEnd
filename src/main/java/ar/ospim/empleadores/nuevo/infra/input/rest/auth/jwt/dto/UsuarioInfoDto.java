package ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInfoDto {
	private UsuarioInfoUsuarioDto usuario;
	private EmpresaDto empresa;
	private PersonaDto persona;
}
 