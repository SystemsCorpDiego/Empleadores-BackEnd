package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;

@Repository
public interface ConvenioRepository  extends JpaRepository< Convenio, Integer>  {
	
	public List<Convenio> findByEmpresaId(Integer empresaId);
	public List<Convenio> findByEmpresaIdAndEstado(Integer empresaId, String estado);
	public List<Convenio> findByEmpresaIdAndCreatedOnBetween(Integer empresaId, LocalDateTime desde, LocalDateTime hasta);
	public List<Convenio> findByEmpresaIdAndEstadoAndCreatedOnBetween(Integer empresaId, String estado, LocalDateTime desde, LocalDateTime hasta);
	
	
	public List<Convenio> findByCreatedOnBetween(LocalDateTime desde, LocalDateTime hasta);
	public List<Convenio> findByCreatedOnBetweenAndEstado(LocalDateTime desde, LocalDateTime hasta, String estado);
	public List<Convenio> findByEstado(String estado);
	
}
