package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DDJJAltaDto {
	
	private Integer id;	
	private LocalDate periodo;
	
	@JsonAlias({"afiliados", "empleados"})
	private List<DDJJEmpleadoDto> empleados;
	
}
