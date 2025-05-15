package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IGestionDeudaDDJJDto {
	 
	  Integer getId();
	  LocalDate getPeriodo();
		Integer getRectificativa();
		String getAporteCodigo();
		String getAporteDescripcion();
	    BigDecimal getImporte();
	    BigDecimal getIntereses(); 
	    
}
