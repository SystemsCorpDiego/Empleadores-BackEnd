package ar.ospim.empleadores.nuevo.infra.input.rest.app;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RedLinkDto {
    private String cuit;
    private LocalDate ddjjPeriodo;

    private Integer secuencia;

    private String aporte;
    private String entidad;

    private BigDecimal importe;

}
