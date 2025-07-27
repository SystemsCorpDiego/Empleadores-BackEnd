package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ConvenioConsultaFiltroDto {
	private Integer empresaId;
	private String estado;
	private String entidad;
	private LocalDate desde;
	private LocalDate hasta;	
}
