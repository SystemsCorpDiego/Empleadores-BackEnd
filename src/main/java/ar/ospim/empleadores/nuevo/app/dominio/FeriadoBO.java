package ar.ospim.empleadores.nuevo.app.dominio;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeriadoBO {
	private Integer id;
	private LocalDate fecha;	
}
