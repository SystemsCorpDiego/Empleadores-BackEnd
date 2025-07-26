package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IGestionDeudaAjustesDto {	 
	
	Integer getId();
	LocalDate getVigencia();
	String getMotivo();
	BigDecimal getImporte();
}
