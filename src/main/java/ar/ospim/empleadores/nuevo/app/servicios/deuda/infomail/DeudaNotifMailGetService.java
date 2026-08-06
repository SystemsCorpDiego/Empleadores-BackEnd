package ar.ospim.empleadores.nuevo.app.servicios.deuda.infomail;

import java.util.List;

import ar.ospim.empleadores.nuevo.dominio.DeudaMailInfoBO;

public interface DeudaNotifMailGetService {
	
	public List<DeudaMailInfoBO> run(); 	 
	
}
