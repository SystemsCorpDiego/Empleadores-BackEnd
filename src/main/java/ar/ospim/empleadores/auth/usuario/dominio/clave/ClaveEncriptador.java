package ar.ospim.empleadores.auth.usuario.dominio.clave;

public interface ClaveEncriptador {

    String codificar(String rawPassword, String salt, String hashAlgorithm);

    boolean iguales(String password, String password1);
}
