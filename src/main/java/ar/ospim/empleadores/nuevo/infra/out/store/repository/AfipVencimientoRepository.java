package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AfipVencimiento;

public interface AfipVencimientoRepository  extends JpaRepository<AfipVencimiento, Integer> {
	 
	public List<AfipVencimiento> findAll();
	
	public List<AfipVencimiento> findByVigenciaBeforeOrderByVigenciaDesc(LocalDate  vigencia);
		
}
