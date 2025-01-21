package ar.ospim.empleadores.auth.usuario.infra.input.servicio.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UsuarioInfoDto {

    private final Integer id;

    private final String usuario;

    private final String clave;

    private final boolean habilitado;

	private LocalDateTime ultimoLogin;

}
