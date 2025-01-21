package ar.ospim.empleadores.nuevo.app.servicios.boleta;

import java.time.LocalDate;

public interface BoletaPagoCalcularVtoService {
	public LocalDate run(String aporte, String cuit, LocalDate periodo);
	public LocalDate getVtoOriginal(String aporte, String cuit, LocalDate periodo);
}
