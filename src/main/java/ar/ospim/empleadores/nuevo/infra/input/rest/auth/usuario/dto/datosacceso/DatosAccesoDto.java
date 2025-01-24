package ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.datosacceso;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NotNull
public class DatosAccesoDto {
    @NotNull(message = "{dto.valid.obligado}")
    @NotBlank(message = "{dto.valid.obligado}")
    private String token;
    
    @NotNull(message = "{dto.valid.obligado}")
    @NotBlank(message = "{dto.valid.obligado}")
    private String usuario;
    
    @NotNull(message = "{dto.valid.obligado}")
    @NotBlank(message = "{dto.valid.obligado}")
    private String clave;

}
