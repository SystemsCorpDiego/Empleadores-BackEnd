package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import lombok.Data;

@Data
public class ConvenioSeteoCuitDto {

	private Integer cuotas ;
	private Integer diasIntencion ;
	private String[] mediosDePago;

}
