package ar.ospim.empleadores.nuevo.app.servicios.usuario;

import lombok.Getter;

@Getter
public enum RegistrarUsuarioEnumException {
    USUARIO_OBLIGADO,
    USUARIO_DUPLICADO,
    USUARIO_PATRON_INVALIDO,
    PASSWORD_PATRON_INVALIDO,
    TOKEN_HABI
    ;

	public String getMsgKey() {
		return "auth.usuario.registrar." + this.name();
	}
	
}
