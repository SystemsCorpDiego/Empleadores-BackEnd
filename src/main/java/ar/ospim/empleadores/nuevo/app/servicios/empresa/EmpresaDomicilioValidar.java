package ar.ospim.empleadores.nuevo.app.servicios.empresa;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaDomicilioBO;

public interface EmpresaDomicilioValidar {
	public void run(EmpresaBO empresa);
	public void run(EmpresaBO empresa, EmpresaDomicilioBO domicilio);
	public void run ( List<EmpresaDomicilioBO> cons, EmpresaDomicilioBO domicilio);
	public void run(EmpresaDomicilioBO domicilio);
	
}
