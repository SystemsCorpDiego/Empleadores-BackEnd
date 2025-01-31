package ar.ospim.empleadores.auth.usuario.dominio.usuario.servicio;

import lombok.Getter;

@Getter
public enum UsuarioStorageEnumException {
	USUARIO_NOT_FOUND,
	USUARIO_ID_NOT_FOUND,
	NOMBRE_DUPLICADO,
	MAIL_DUPLICADO,
	USUARIO_CLAVE_NOT_FOUND,
    USUARIO_DUPLICADO,
    MAIL_SIN_USUARIO,
	;
	
	public String getMsgKey() {
		return "auth.usuario.storage." + this.name();
	}		
}
