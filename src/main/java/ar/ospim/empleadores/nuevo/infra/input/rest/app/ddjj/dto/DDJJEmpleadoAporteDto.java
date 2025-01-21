package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter  
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DDJJEmpleadoAporteDto {
	private Integer id;
	private String codigo;
	private String descripcion;
	private BigDecimal importe;
}
