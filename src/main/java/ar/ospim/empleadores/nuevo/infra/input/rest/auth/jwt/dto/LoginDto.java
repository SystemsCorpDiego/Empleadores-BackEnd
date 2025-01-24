package ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;


public class LoginDto  implements Serializable {
	
	private static final long serialVersionUID = -468230957598564934L;

	@NotNull(message = "{dto.valid.obligado}")
	@NotBlank(message = "{dto.valid.obligado}")
	public final String usuario;
	
	@NotNull(message = "{dto.valid.obligado}")
	@NotBlank(message = "{dto.valid.obligado}")
	public final String clave;		

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public LoginDto(
			@JsonProperty("usuario") String usuario,
			@JsonProperty("clave") String clave) {
		this.usuario = usuario;
		this.clave = clave;
	}

	@Override
	public String toString() {
		return "LoginDto [usuario=" + usuario + ", clave=" + clave + "]";
	}

}
