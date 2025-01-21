package ar.ospim.empleadores.nuevo.infra.input.rest.auth.rol.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FuncionalidadesDto {
	 private Integer id;
     private String descripcion;
     private boolean activo;
}
