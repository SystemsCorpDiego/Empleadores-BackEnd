package ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink;

import ar.ospim.empleadores.comun.exception.WebServiceException;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;

public interface RedLinkService { 

	public String generarBep(BoletaPagoBO boleta ) throws WebServiceException;
	
	public String generarBepSinValidarBoleta(BoletaPagoBO boleta, String deudaIdDeuda) throws WebServiceException;
	
}