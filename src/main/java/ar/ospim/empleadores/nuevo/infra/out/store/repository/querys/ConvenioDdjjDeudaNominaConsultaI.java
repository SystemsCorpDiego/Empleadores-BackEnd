package ar.ospim.empleadores.nuevo.infra.out.store.repository.querys;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ConvenioDdjjDeudaNominaConsultaI {

	Integer getId();
	
	String getAporte();
	BigDecimal getImporte();
	BigDecimal getInteres();
	LocalDate getVencimiento();	
	Integer getBoletaId();

}
