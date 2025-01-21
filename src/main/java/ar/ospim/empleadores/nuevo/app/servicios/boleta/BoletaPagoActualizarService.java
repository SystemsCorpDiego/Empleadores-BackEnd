package ar.ospim.empleadores.nuevo.app.servicios.boleta;

import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoValidaModiDto;

public interface BoletaPagoActualizarService {
	public BoletaPagoBO run(BoletaPagoBO bo);
	public BoletaPagoValidaModiDto puedeActualizar(BoletaPagoBO reg );
}
