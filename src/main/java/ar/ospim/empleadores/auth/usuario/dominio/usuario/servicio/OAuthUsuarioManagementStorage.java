package ar.ospim.empleadores.auth.usuario.dominio.usuario.servicio;

import ar.ospim.empleadores.auth.usuario.dominio.usuario.modelo.OAuthUsuarioBo;

public interface OAuthUsuarioManagementStorage {

    void crearUsuario(OAuthUsuarioBo oAuthUsuarioBo);

    void actualizarUsuario(String usuarioActual, OAuthUsuarioBo newUsuarioData);

}
