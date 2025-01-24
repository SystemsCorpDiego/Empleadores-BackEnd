package ar.ospim.empleadores.nuevo.infra.input.rest.app.ajuste.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AjusteDto {
	private Integer id;
	private String cuit;
	private String razonSocial;
	private LocalDate periodo_original;
	private BigDecimal importe;
	private String aporte;
	private String motivo;
	private LocalDate vigencia;
	private String boleta;
}
