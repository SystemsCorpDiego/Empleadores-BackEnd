package ar.ospim.empleadores.nuevo.app.servicios.mail;

import ar.ospim.empleadores.auth.dfa.dominio.SetDFABo;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.dominio.MailBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;

public interface MailService {

	public void run(MailBO email) ;
	
	public void runCambioDeClave(String usuario, String claveNueva, String usuarioMail,  String usuarioModificaMail);
	
	public void runMailActivacionCuenta(UsuarioBO usuarioBO, String usuarioMail, SetDFABo  dfaDto);
	public void runMailActivacionCuenta(UsuarioBO usuarioBO, String usuarioMail);
	
	public void runMailCuentaEmpresaNuevaInfo(EmpresaBO empresa);
	
	public void runMailRecuperoClave(String mail,  String usuario, String token);
	public void runMailRecuperoClave(String mail,  String usuario, String token, SetDFABo  dfaDto);
	
	public 	void runMailConvenioPresentado(String mailEmpresa, Convenio convenio);
	
}
