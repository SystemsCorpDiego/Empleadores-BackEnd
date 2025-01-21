package ar.ospim.empleadores.nuevo.app.servicios.empresa;

import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.comun.infra.input.service.BasicDataEmpresaDto;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;

public interface ConsultarEmpresa {
	
	public List<EmpresaBO> findAll();
	public Optional<EmpresaBO> findEmpresa(Integer id) ;
	public Optional<EmpresaBO> findEmpresa(String cuit) ;
	public EmpresaBO getEmpresa(Integer id);
	public EmpresaBO getEmpresa(String cuit);
	BasicDataEmpresaDto getBasicDataEmpresa(Integer empresaId);

}
