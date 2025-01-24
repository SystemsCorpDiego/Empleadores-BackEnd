package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DDJJPeriodoInfoDto {

	private String estado;
	private LocalDate fechaPresentacion; 
	private LocalDate fechaCreacion;
	private Integer secuencia;
	private boolean boletaPago;
	
}
