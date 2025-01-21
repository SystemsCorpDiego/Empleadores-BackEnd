package ar.ospim.empleadores.nuevo.infra.input.rest.app.publicaciones.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PublicacionDto {
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer id;
	
	@NotBlank(message = "{dto.valid.obligado}")
	@Length(max = 150, message = "{dto.valid.longitud}")
	private String titulo;
	
	@NotBlank(message = "{dto.valid.obligado}")
	@Length(max = 500, message = "{dto.valid.longitud}")
	private String cuerpo;

	@NotNull(message = "{dto.valid.obligado}")
	private LocalDate vigenciaDesde;

	@NotNull(message = "{dto.valid.obligado}")
	private LocalDate vigenciaHasta;

}
