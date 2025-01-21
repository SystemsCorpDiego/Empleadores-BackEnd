package ar.ospim.empleadores.nuevo.infra.input.rest.auth.rol.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RolFuncionalidadAltaDto {
	private String descripcion;
	private Short id;
	private List<FuncionalidadesDto> funcionalidades;
}
