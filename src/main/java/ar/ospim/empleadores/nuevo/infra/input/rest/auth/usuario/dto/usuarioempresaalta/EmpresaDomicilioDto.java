package ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.usuarioempresaalta;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmpresaDomicilioDto {
	
    //@NotNull(message = "{dto.valid.obligado}")
    private String tipo;
    
	//@NotNull(message = "{dto.valid.obligado}")
	private Integer provinciaId;
	//@NotNull(message = "{dto.valid.obligado}")
	private Integer localidadId;

	//@NotNull(message = "{dto.valid.obligado}")
	private String calle;
	
	@JsonProperty("numeroDomicilio")
	private String calleNro;
	private String piso;
	private String depto;
	private String oficina;
	private String cp;
	private String planta;
	
}
