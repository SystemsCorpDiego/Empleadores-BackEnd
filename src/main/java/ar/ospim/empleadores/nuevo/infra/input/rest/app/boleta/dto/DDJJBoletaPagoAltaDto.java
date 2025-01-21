package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class DDJJBoletaPagoAltaDto {

	private String codigo;
	private LocalDate intencionDePago;
	private String formaDePago;
	
}
