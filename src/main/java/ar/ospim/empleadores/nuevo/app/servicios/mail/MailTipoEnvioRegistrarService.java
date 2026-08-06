package ar.ospim.empleadores.nuevo.app.servicios.mail;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.MailTipoEnvio;

public interface MailTipoEnvioRegistrarService {

	MailTipoEnvio run(MailTipoEnvio reg);
	
}
