package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.VEscalaSalarial;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.EscalaSalarialAntiguedadI;

@Repository
public interface EscalaSalarialRepository extends JpaRepository<VEscalaSalarial, Integer>  {

	public List<VEscalaSalarial> findByTipoAndCamaraAndCategoriaAndAntiguedadAndVigenciaBeforeOrderByVigenciaDesc(String tipo, String camara, String categoria, Integer antiguedad, LocalDate vigencia);
	 	
	@Query(value="select distinct camara, antiguedaddesde antiguedad from tablaescalasalarialjornales order by 1,2", nativeQuery=true)  
	public List<EscalaSalarialAntiguedadI> getAntiguedades();
	
}
