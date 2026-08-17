package ar.ospim.empleadores.nuevo.infra.out.store;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.ospim.empleadores.nuevo.dominio.MailTipoConfiguracionBO;


public interface MailTipoConfiguracionStorage {

	Optional<MailTipoConfiguracionBO> findVigente(Integer mailId);
	List<MailTipoConfiguracionBO> findAll();
	MailTipoConfiguracionBO save(MailTipoConfiguracionBO reg);
	
	public Optional<LocalDate> getFechaEnvioDesde(Integer mailId);
	public Optional<LocalDate> getFechaEnvioHasta();
	
}
