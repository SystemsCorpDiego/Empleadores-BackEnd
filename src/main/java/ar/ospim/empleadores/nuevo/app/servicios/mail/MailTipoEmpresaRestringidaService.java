package ar.ospim.empleadores.nuevo.app.servicios.mail;

import java.util.List;

import ar.ospim.empleadores.nuevo.dominio.MailTipoEmpresaRestringidaBO;

public interface MailTipoEmpresaRestringidaService {

	public MailTipoEmpresaRestringidaBO registrar(MailTipoEmpresaRestringidaBO reg);
	public void borrar(Integer id);
	public List<MailTipoEmpresaRestringidaBO> consultar(Integer mailId);
	public Boolean esRestringido(String cuit);

}
