package ar.ospim.empleadores.auth.jwt.dominio.usuario;

import lombok.Getter;

@Getter
public class UsuarioInfoBo {

    private final Integer id;
    private final String nombre;
    private final boolean habilitado;
    private final String clave;

    public UsuarioInfoBo(Integer id, String nombre, boolean habilitado, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.habilitado = habilitado;
        this.clave = clave;
    }
}
