package ar.ospim.empleadores.nuevo.app.servicios.empresa;

import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO; 

public interface EmpresaService {
	
	public List<EmpresaBO> findAll();
	
	EmpresaBO getEmpresa(Integer id);
	EmpresaBO getEmpresa(String cuit);
	
	Optional<EmpresaBO> findEmpresa(Integer id);
	
	EmpresaBO validarEmpresa(Integer id, String cuit);
	
	EmpresaBO addEmpresa(EmpresaBO empresa);
	
	public EmpresaBO updateEmpresa(EmpresaBO empresa);
	 
	
}
