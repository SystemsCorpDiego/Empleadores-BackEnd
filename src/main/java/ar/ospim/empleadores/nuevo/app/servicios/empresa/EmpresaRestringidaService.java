package ar.ospim.empleadores.nuevo.app.servicios.empresa;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaRestringidaBO;

public interface EmpresaRestringidaService {

	public EmpresaRestringidaBO crear(EmpresaRestringidaBO feriado);	
	public void borrar(Integer Id);	
	public List<EmpresaRestringidaBO> consultar();   
	public Boolean esRestringido(String cuit);
	   
}
