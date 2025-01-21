package ar.ospim.empleadores.nuevo.infra.input.rest.app.formapago;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FormaPagoDto {
	private String codigo;
	private String descripcion;
}
