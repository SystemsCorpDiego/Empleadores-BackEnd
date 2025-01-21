package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DDJJTotalesAportesDto {
	   private String codigo;
       private BigDecimal importe;
}
