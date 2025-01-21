package ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInfoUsuarioDto {
	private String nombre;
	private List<UsuarioInfoUsuarioRolDto> rol;
	private List<FuncionalidadesAsignadasDto> funcionalidad;

}
