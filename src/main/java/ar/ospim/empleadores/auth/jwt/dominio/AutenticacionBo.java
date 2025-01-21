package ar.ospim.empleadores.auth.jwt.dominio;

import ar.ospim.empleadores.auth.jwt.dominio.usuario.UsuarioInfoBo;
import lombok.Getter;

@Getter
public class AutenticacionBo {

    private final UsuarioInfoBo usuario;

    public AutenticacionBo(UsuarioInfoBo usuario) {
        this.usuario = usuario;
    }
}
