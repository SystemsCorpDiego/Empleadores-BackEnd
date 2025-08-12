package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ConvenioSeteoDto {

	private Integer id;
		
	private String cuit;
	
	private Integer cuotas;
	
	private Integer antiguedad;
	
	@JsonProperty("diasIntencion")
    private Integer intencionPagoDiasMax;
	
	private BigDecimal tasa;
	
	@JsonProperty("ventanilla")
    private Boolean formaPagoVentanilla;
	
	@JsonProperty("redlink")
	private Boolean formaPagoRedlink;
	
	@JsonProperty("banelco")
	private Boolean formaPagoBanelco;
	
	@JsonProperty("cheque")
	private Boolean formaPagoCheque;
	
	@JsonProperty("vigDesde")	
	private LocalDate desde;
	
	@JsonProperty("vigHasta")	
	private LocalDate hasta;
	
}
