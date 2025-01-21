package ar.ospim.empleadores.nuevo.app.servicios.boleta;

import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;

public interface BoletaPagoDDJJActualizarService {

	public BoletaPagoBO run( BoletaPagoBO bo);
	public Boolean puedeActualizar(BoletaPagoBO reg );
	
}
