package ar.ospim.empleadores.auth.usuario.app;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.RolBO;

public interface RolService {
	
	public RolBO crear(RolBO registro);	
	public void borrar(Short id);	
	public List<RolBO> consultar();
	public List<RolBO> usuarioInternoConsultar();   
	public RolBO consultar(Short id );   
	

}