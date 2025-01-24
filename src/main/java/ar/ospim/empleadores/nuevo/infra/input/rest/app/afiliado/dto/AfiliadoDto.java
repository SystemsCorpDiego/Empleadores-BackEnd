package ar.ospim.empleadores.nuevo.infra.input.rest.app.afiliado.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AfiliadoDto {
	private String cuil;
    private Integer inte;
    private String apellido;
    private String nombre;
}
