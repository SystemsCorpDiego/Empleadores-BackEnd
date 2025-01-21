package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import ar.ospim.empleadores.nuevo.app.servicios.empresa.validaciones.ContactoTipoValid;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto.LocalidadDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto.ProvinciaDto;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.EmpresaDomicilioTipoEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmpresaDomicilioDto {
    private Integer id;
    
	@NotBlank(message = "{dto.valid.obligado}")
	@ContactoTipoValid(enumClass = EmpresaDomicilioTipoEnum.class)
	private String tipo;

	@NotNull(message = "{dto.valid.obligado}")
	//private Integer provinciaId;
	private ProvinciaDto provincia;
	
	@NotNull(message = "{dto.valid.obligado}")
    //private Integer localidadId;
	private LocalidadDto localidad;
	
	@NotBlank(message = "{dto.valid.obligado}")
    @Length(max = 200, message = "{dto.valid.longitud}")
    private String calle;
    
	private String calleNro;
    private String piso;
    private String depto;
    private String oficina;
    private String cp;
    private String planta;    
}
