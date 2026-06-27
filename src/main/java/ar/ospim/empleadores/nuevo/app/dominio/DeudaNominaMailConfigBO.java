package ar.ospim.empleadores.nuevo.app.dominio;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeudaNominaMailConfigBO {

	private Long id;
	private LocalDate fechaEnvio;
	private String cuerpoMail;

}
