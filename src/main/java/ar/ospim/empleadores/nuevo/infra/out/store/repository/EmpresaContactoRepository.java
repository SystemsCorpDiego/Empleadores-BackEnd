package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.EmpresaContacto;

@Repository
public interface EmpresaContactoRepository extends JpaRepository<EmpresaContacto, Integer> {
	
	public List<EmpresaContacto> findByEmpresaId(Integer id);

	public List<EmpresaContacto> findByEmpresaIdAndTipo(Integer empresaId, String tipo);
	
}
