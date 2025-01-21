package ar.ospim.empleadores.auth.usuario.dominio.usuario.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OAuthUsuarioBo {

    private String usuario;

    private String clave;

    private String nombre;

    private String apellido;

    private String email;

	@Override
	public String toString() {
		return "OAuthUsuarioBo [usuario=" + usuario + ", clave=" + clave + ", nombre=" + nombre + ", apellido="
				+ apellido + ", email=" + email + "]";
	}
    
}
