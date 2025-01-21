package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.EmpresaDomicilio;

@Repository
public interface EmpresaDomicilioRepository  extends JpaRepository<EmpresaDomicilio, Integer> {
	public List<EmpresaDomicilio> findByEmpresaId(Integer id);
	
	@Query(value =" select count(1) FROM ddjj_deta "
			+ " where empresa_domicilio_id = ?1 ", 
						nativeQuery = true)
	public Integer domiDDJJCount(Integer domicilioId);   
	
	public List<EmpresaDomicilio> findByEmpresaIdAndTipo(Integer id, String tipo);
	
}
