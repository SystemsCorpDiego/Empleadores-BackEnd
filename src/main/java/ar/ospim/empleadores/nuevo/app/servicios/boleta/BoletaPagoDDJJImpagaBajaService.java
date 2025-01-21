package ar.ospim.empleadores.nuevo.app.servicios.boleta;

import java.time.LocalDate;

public interface BoletaPagoDDJJImpagaBajaService {

	public void run(Integer empresaId, LocalDate periodo);
	
}
