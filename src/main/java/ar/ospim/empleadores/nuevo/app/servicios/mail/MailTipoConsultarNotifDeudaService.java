package ar.ospim.empleadores.nuevo.app.servicios.mail;

import java.util.List;

import ar.ospim.empleadores.nuevo.dominio.MailTipoDeudaInfoBO;

public interface MailTipoConsultarNotifDeudaService {
	
	public List<MailTipoDeudaInfoBO> run(); 	 
	
}
