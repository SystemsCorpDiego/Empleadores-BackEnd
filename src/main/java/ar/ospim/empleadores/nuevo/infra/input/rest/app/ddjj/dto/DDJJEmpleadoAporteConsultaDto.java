package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DDJJEmpleadoAporteConsultaDto {
	private String codigo;
	private BigDecimal importe;
}
