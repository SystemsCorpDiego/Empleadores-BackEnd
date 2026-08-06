package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.dominio.MailTipoConfiguracionBO;

public interface MailTipoConfiguracionStorage {

	Optional<MailTipoConfiguracionBO> findVigente(Integer mailId);
	List<MailTipoConfiguracionBO> findAll();
	MailTipoConfiguracionBO save(MailTipoConfiguracionBO reg);
		
}
