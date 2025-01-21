package ar.ospim.empleadores.nuevo.app.servicios.empresa;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.ContactoBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;

public interface EmpresaContactoValidar {

	public void run (ContactoBO contacto);
	
	public void run (EmpresaBO empresa);
	public void run (EmpresaBO empresa, ContactoBO contacto);
	
	public void run (List<ContactoBO> contactos, ContactoBO contacto);
	
}
