package ar.ospim.empleadores.auth.jwt.dominio;

public class TokenLoginBo {

	public final TokenTipoEnum tipo;

	public final String usuario;

	public final Integer usuarioId;

	public TokenLoginBo(TokenTipoEnum type, String usuario, Integer usuarioId) {
		this.tipo = type;
		this.usuario = usuario;
		this.usuarioId = usuarioId;
	}

	public boolean isType(TokenTipoEnum tipo) {
		return this.tipo == tipo;
	}
}
