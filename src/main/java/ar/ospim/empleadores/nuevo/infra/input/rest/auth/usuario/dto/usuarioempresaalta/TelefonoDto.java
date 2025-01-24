package ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.usuarioempresaalta;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TelefonoDto {
	private String prefijo;
	private String nro;
}
