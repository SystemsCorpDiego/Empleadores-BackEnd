package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ConvenioInteresService {
	public  BigDecimal calcularInteres(String cuit, BigDecimal capital, LocalDate desde, LocalDate hasta);
	
}
