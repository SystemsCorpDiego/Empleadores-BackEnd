package ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.usuarioempresaalta;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UsuarioEmpresaAltaDto {

	@NotBlank(message = "{dto.valid.obligado}")
	@Length(max = 11, message = "{dto.valid.longitud}")
	private String cuit;

	@NotBlank(message = "{dto.valid.obligado}")
    @Length(max = 200, message = "{dto.valid.longitud}")
    private String razonSocial;

	@NotBlank(message = "{dto.valid.obligado}")
	private String clave; 
	
    @NotBlank(message = "{dto.valid.obligado}")
    @Length(max = 200, message = "{dto.valid.longitud}")
    private String email;

    @NotNull(message = "{dto.valid.obligado}")
    private boolean actividad_molinera;
    
    private List<String> emailAlternativos;
    
    @NotBlank(message = "{dto.valid.obligado}")
    @Length(max = 50, message = "{dto.valid.longitud}")
    private String telefono;
    
    @NotBlank(message = "{dto.valid.obligado}")
    @Length(max = 10, message = "{dto.valid.longitud}")
    private String telefono_prefijo;

    @NotBlank(message = "{dto.valid.obligado}")
    @Length(max = 50, message = "{dto.valid.longitud}")
    private String whatsapp;

    @NotBlank(message = "{dto.valid.obligado}")
    @Length(max = 10, message = "{dto.valid.longitud}")
    private String whatsapp_prefijo; 

    private List<TelefonoDto> telefonosAlternativos;
    
    @NotNull(message = "{dto.valid.obligado}")
    private List<EmpresaDomicilioDto> domicilios;
    
}
