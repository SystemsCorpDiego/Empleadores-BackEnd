package ar.ospim.empleadores.auth.usuario.dominio.usuarioclave;

import lombok.Getter;

@Getter
public enum UsuarioClaveEnumException {
	NULL_TIEMPO_CREACION,
    NULL_CLAVE,
    NULL_SALT,
    NULL_HASH_ALGORITHM,
    ;

	public String getMsgKey() {
		return "BO.usuarioClave." + this.name();
	}

}
