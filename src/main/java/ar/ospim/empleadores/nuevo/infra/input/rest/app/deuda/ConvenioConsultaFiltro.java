package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ConvenioConsultaFiltro {
	private Integer empresaId;
	private String estado;
	private LocalDate desde;
	private LocalDate hasta;	
}
