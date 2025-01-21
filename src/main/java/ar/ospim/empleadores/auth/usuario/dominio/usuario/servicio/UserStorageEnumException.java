package ar.ospim.empleadores.auth.usuario.dominio.usuario.servicio;

import lombok.Getter;

@Getter
public enum UserStorageEnumException {
    NOT_FOUND_USER,
    NOT_FOUND_USER_PASSWORD,
    DUPLICATE_USER
    ;

}
