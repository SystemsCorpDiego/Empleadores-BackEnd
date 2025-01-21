package ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.pagomiscuentas;

import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;

public interface PagoMisCuentasService {

	public String generarBep(BoletaPagoBO boleta);
	
}
