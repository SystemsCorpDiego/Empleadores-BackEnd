package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaDomicilioBO;

public interface EmpresaDomicilioStorage {
	
	public Integer domiDDJJCount(Integer domicilioId);   
	public List<EmpresaDomicilioBO> findAll(Integer domicilioId);   
	public Optional<EmpresaDomicilioBO> findByIdAndEmpresaId(Integer empresaId, Integer id);
	public List<EmpresaDomicilioBO> findByEmpresaId(Integer empresaId);
	public List<EmpresaDomicilioBO> findByEmpresaIdAndTipo(Integer empresaId, String tipo);
		
	public EmpresaDomicilioBO save(Integer empresaId, EmpresaDomicilioBO empresaDomicilioBO);
	public void deleteById(Integer domicilioId);
}
