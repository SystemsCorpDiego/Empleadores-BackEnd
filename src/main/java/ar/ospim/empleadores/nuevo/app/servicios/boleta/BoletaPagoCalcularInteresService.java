package ar.ospim.empleadores.nuevo.app.servicios.boleta;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface BoletaPagoCalcularInteresService {

	public BigDecimal run(String aporte, String cuit, LocalDate periodo, LocalDate intencionDePago, BigDecimal importeAPagar);
	
	public BigDecimal run(LocalDate vencimiento, LocalDate intencionDePago, BigDecimal importeAPagar);
	
}
