package ar.ospim.empleadores.nuevo.infra.input.rest.app;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PagoMisCuentasDto {
	
    private String cuit;

    private Integer secuencia;

    private String aporte;
    private String entidad;

    private BigDecimal importe;

}
