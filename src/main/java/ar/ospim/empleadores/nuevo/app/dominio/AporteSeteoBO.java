package ar.ospim.empleadores.nuevo.app.dominio;

import java.time.LocalDate;

import lombok.Data;


@Data
public class AporteSeteoBO {
	private Integer id;
	private String aporte;
	private LocalDate desde;
	private LocalDate hasta;
	private EntidadBO entidad;
	private boolean esSocio;
	private CalculoBO calculo;
	private CamaraBO camara;
	private String categoria;
	private Integer antiguedad;
}
