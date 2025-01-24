package ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UsuarioInfoUsuarioDto {
	private String nombre;
	private List<UsuarioInfoUsuarioRolDto> rol;
	private List<FuncionalidadesAsignadasDto> funcionalidad;

}
