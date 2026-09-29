package ar.ospim.empleadores.nuevo.app.servicios.mail;

import java.util.List;

import ar.ospim.empleadores.nuevo.dominio.MailTipoBO;

public interface MailTipoConsultaService {
	public List<MailTipoBO> consultar();
}
