package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoletaPagoConsSinDDJJDto {
	private Integer id;
	
	private String cuit;
	private String razonSocial;

	
	@JsonProperty("numero_boleta")
    private Integer numeroBoleta;
	
	private String entidad;
	private String nroActa;
	private String razonDePago;
	private LocalDate intencionDePago;
	private BigDecimal importe;
}
