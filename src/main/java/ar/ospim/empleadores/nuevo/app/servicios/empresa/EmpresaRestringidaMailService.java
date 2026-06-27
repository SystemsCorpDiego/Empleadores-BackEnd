package ar.ospim.empleadores.nuevo.app.servicios.empresa;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaRestringidaMailBO;

public interface EmpresaRestringidaMailService {

	public EmpresaRestringidaMailBO crear(EmpresaRestringidaMailBO reg);
	public void borrar(Integer id);
	public List<EmpresaRestringidaMailBO> consultar();
	public Boolean esRestringido(String cuit);

}
