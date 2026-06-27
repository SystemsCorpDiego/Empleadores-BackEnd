package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeudaNominaMailConfigDto {

	private Long id;
	private LocalDate fechaEnvio;
	private String cuerpoMail;

}
