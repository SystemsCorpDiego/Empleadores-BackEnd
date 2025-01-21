package ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter 
@ToString
public class UsuarioInternoDto {
		private Short id;
	    private String descripcion;
	     
	    private Short rolId;
	    private String nombre;
	    private String apellido;
	    private String email;
	    private Boolean habilitado;
	    private Boolean notificaciones;
}
