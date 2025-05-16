package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IGestionDeudaAjustes {
	Integer getId();
	LocalDate getVigencia();
	String getMotivo();
	BigDecimal getImporte();
}
