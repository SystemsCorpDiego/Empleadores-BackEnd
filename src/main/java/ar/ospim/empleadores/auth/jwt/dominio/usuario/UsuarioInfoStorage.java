package ar.ospim.empleadores.auth.jwt.dominio.usuario;

public interface UsuarioInfoStorage {

	UsuarioInfoBo getUsuario(String username);
	UsuarioInfoBo getUsuario(Integer userId);
    void actualizarLoginDate(String username);
    Boolean obtenerUsuarioConDFAHabilitado(Integer userId);

}
