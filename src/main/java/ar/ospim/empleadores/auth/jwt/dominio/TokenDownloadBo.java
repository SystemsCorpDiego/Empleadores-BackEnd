package ar.ospim.empleadores.auth.jwt.dominio;

public class TokenDownloadBo {

	public final TokenTipoEnum tipo;

	public final String entidad;

	public final Integer empresaId;
	
	public final String cuit;

	public TokenDownloadBo(TokenTipoEnum type, String entidad, Integer empresaId, String cuit) {
		this.tipo = type;
		this.entidad = entidad;
		this.empresaId = empresaId;
		this.cuit = cuit;
	}

	public boolean isType(TokenTipoEnum tipo) {
		return this.tipo == tipo;
	}
	
}
