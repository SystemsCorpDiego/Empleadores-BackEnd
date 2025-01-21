package ar.ospim.empleadores.auth.usuario.dominio.usuarioclave;

import java.util.Objects;

import ar.ospim.empleadores.comun.exception.BusinessException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioClaveBo {

    private String clave;

    private  String salt = "salt";

    private  String hashAlgorithm = "hashAlgorithm";

    public UsuarioClaveBo(String password, String salt, String hashAlgorithm) {
        validaciones(password, salt, hashAlgorithm);
        this.clave = password;
        this.salt = salt;
        this.hashAlgorithm = hashAlgorithm;
    }

    private void validaciones(String password, String salt, String hashAlgorithm) {
    	
        Objects.requireNonNull(password, () -> {
            throw new BusinessException(UsuarioClaveEnumException.NULL_CLAVE.name(), "La contraseña es obligatoria");
        });

        Objects.requireNonNull(salt, () -> {
            throw new BusinessException(UsuarioClaveEnumException.NULL_SALT.name(), "El salt es obligatorio");
        });

        Objects.requireNonNull(hashAlgorithm, () -> {
            throw new BusinessException(UsuarioClaveEnumException.NULL_HASH_ALGORITHM.name(), "El algoritmo de hashing es obligatorio");
        });
    }

}
