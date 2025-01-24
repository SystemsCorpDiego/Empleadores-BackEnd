package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DDJJBoletaArmadoDetalleAjusteDto {

	private String descripcion;
	private String motivo;
	private String motivoDescripcion;
	private BigDecimal monto;
	
}
