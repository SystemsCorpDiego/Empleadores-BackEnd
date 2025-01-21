package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import ar.ospim.empleadores.nuevo.app.servicios.empresa.validaciones.ContactoTipoValid;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.EmpresaContactoTipoEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmpresaContactoDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;
	
	@NotBlank(message = "{dto.valid.obligado}")
	@ContactoTipoValid(enumClass = EmpresaContactoTipoEnum.class)
	private String tipo;

	
    @Length(max = 10, message = "{dto.valid.longitud}")
	private String prefijo;

	@NotBlank(message = "{dto.valid.obligado}")
    @Length(max = 200, message = "{dto.valid.longitud}")
	private String valor;
 
}
