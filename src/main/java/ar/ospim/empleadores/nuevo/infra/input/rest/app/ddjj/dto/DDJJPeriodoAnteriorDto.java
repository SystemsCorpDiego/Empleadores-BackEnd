package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class DDJJPeriodoAnteriorDto {	

	private Integer id;
	private LocalDate periodo;
	private Integer secuencia;
	
	private List<DDJJPeriodoAnteriorAfiliadoDto> afiliados; 
     
}
