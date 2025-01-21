package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DDJJBoletaArmadoDetalleAjusteDto {

	private String descripcion;
	private String motivo;
	private String motivoDescripcion;
	private BigDecimal monto;
	
}
