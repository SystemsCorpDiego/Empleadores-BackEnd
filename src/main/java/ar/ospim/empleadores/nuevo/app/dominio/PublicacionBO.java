package ar.ospim.empleadores.nuevo.app.dominio;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicacionBO {
	private Integer id;
	
	private String titulo;
	
	private String cuerpo;

	private LocalDate vigenciaDesde;

	private LocalDate vigenciaHasta;
}
