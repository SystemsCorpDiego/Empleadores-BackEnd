package ar.ospim.empleadores.auth.usuario.app;

public interface UsuarioActivarPorToken {

	public String run ( String token );
	public String crearToken(String usuario );
}
