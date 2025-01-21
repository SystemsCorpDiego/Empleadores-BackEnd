package ar.ospim.empleadores.auth.usuario.app;

import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;

public interface TokenGestionUsuario {

	public String crearParaUsuario(UsuarioBO usuario, String tokenDFA);
	public String crearParaUsuario(UsuarioBO usuario);	
	public String crearParaMail(UsuarioBO usuario, String mail);
	public String crearParaMail(UsuarioBO usuario, String mail, String tokenDFA);
	public String getUsuario(String token);
	public Integer getId(String token);
	public String getMail(String token);
	public String getTokenDFA(String token);
	
}
