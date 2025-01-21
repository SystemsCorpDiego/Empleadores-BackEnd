package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.ContactoBO;

public interface EmpresaContactoStorage {
	
	public Optional<ContactoBO> findById(Integer id);
	public List<ContactoBO> findAll(Integer domicilioId);   
	
	public Optional<ContactoBO> findByEmpresaIdAndId(Integer empresaId, Integer id);
	public List<ContactoBO> findByEmpresaIdAndTipo(Integer empresaId,String tipo);
	public List<ContactoBO> findByEmpresaId(Integer empresaId);
	
	public ContactoBO save(Integer empresaId, ContactoBO empresaContactoBO);
	public void deleteById(Integer contactoId);
}
