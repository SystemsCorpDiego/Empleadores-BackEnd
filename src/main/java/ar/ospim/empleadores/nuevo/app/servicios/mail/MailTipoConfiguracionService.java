package ar.ospim.empleadores.nuevo.app.servicios.mail;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.dominio.MailTipoConfiguracionBO;

public interface MailTipoConfiguracionService {

	public List<MailTipoConfiguracionBO> consultar(Integer mailId);
	public Optional<MailTipoConfiguracionBO> consultarVigente(Integer mailId);
	
	MailTipoConfiguracionBO crear(MailTipoConfiguracionBO reg);
	MailTipoConfiguracionBO actualizar(Integer id, MailTipoConfiguracionBO reg);

	public Optional<LocalDate> getFechaEnvioDesde(Integer mailId);
	public Optional<LocalDate> getFechaEnvioHasta();
}
