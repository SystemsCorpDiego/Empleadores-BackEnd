package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoletaPagoConsDto {
	
	private List<BoletaPagoConsConDDJJDto> con_ddjj;
	private List<BoletaPagoConsSinDDJJDto> sin_ddjj;
	
}
