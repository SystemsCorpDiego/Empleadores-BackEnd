package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DDJJConsultaFiltroDto {
	private LocalDate desde;
	private LocalDate hasta;
	private String cuit;
	private Integer empresaId;
}
