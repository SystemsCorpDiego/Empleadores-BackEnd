package ar.ospim.empleadores.nuevo.infra.input.rest.app.feriado.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeriadoDto {
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer id;
	
	//@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") 
	@NotNull(message = "{dto.valid.obligado}")
	private LocalDate fecha;

	
}
