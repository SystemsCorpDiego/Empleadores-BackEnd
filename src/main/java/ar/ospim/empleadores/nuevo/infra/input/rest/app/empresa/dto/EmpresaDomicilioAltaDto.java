package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto;

import org.hibernate.validator.constraints.Length;

import ar.ospim.empleadores.nuevo.app.servicios.empresa.validaciones.ContactoTipoValid;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.EmpresaDomicilioTipoEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class EmpresaDomicilioAltaDto {
	
	//@NotBlank(message = "{dto.valid.obligado}")
	@ContactoTipoValid(enumClass = EmpresaDomicilioTipoEnum.class)
	private String tipo;

	//@NotNull(message = "{dto.valid.obligado}")
	private Integer provinciaId;
	
	//@NotNull(message = "{dto.valid.obligado}")
    private Integer localidadId;
	
	//@NotBlank(message = "{dto.valid.obligado}")
    @Length(max = 200, message = "{dto.valid.longitud}")
    private String calle;
    
	private String calleNro;
    private String piso;
    private String depto;
    private String oficina;
    private String cp;
    private String planta;    
}
