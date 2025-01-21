package ar.ospim.empleadores.auth.usuario.dominio;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ClaveResetTokenBo {

    private Integer id;

    private String token;

    private Integer usuarioId;

    private Boolean habilitado ;

    private LocalDateTime expira;
}
