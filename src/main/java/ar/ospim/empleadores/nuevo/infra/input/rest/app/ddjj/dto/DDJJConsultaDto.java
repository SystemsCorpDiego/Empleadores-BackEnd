package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DDJJConsultaDto {
	private Integer id;	
	private String estado;
	private Integer empresaId;	
	private LocalDate periodo;
	private Integer secuencia;	
	private List<DDJJEmpleadoConsultaDto> afiliados;	
}
