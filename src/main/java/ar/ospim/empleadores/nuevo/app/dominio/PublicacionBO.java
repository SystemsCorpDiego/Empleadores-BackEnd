package ar.ospim.empleadores.nuevo.app.dominio;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PublicacionBO {
	private Integer id;
	
	private String titulo;
	
	private String cuerpo;

	private LocalDate vigenciaDesde;

	private LocalDate vigenciaHasta;
}
