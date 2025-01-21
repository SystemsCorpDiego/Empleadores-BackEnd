package ar.ospim.empleadores.nuevo.app.dominio;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioInternoBO extends UsuarioBO {
	
	private PersonaBO persona;
	
	
	public UsuarioInternoBO(UsuarioBO usuario, PersonaBO persona) {
		super(usuario.getId(), usuario.getDescripcion(), usuario.isHabilitado(), null);
		this.persona = persona;
	}
	
	
	public UsuarioInternoBO(
			Integer id, String descripcion, boolean habilitado, LocalDateTime ultimoLogin, LocalDateTime previoLogin,
			String nombre, String apellido, String email, boolean notificaciones ) {
		
		super(id, descripcion, habilitado, null);
		this.setPrevioLogin(previoLogin);
		this.setUltimoLogin(ultimoLogin);
		persona = new PersonaBO(); 
		this.persona.setApellido(apellido);
		this.persona.setNombre(nombre);
		this.persona.setEmail(email);
		this.persona.setNotificaciones(notificaciones);
	}
	
}
