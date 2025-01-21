package ar.ospim.empleadores.nuevo.infra.input.rest.app.aporte.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AporteSeteoDto {

	private Integer id;
	private String aporte;
	private LocalDate desde;
	private LocalDate hasta;
	private String entidad;
	private boolean socio;
	private String calculoTipo;
	private String calculoValor;
	private String calculoBase;
	private String camara;
	private String camaraCategoria;
	private String camaraAntiguedad;
	
}
