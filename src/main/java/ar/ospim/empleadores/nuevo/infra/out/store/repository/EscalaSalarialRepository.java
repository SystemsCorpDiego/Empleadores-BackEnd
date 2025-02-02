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

	/*
	@Query(value= " select max(s.categoria) categoria from v_escala_salarial s "
						  + " where s.basico > 0 "
						  + " and s.tipo = ?1 "
						  + " and s.camara = ?2 "
						  + " and s.antiguedad = ?3 "
						  + "and  s.vigencia = ( select max(v.vigencia) from v_escala_salarial v "
						  + "					 where  v.tipo     = s.tipo "
						  + "					 and    v.camara   = s.camara "
						  + "					 and   v.antiguedad = s.antiguedad "
						  + "                  and   v.basico > 0 "
						  + "					 and   v.vigencia <= ?4 ) "
						  + "", 
				nativeQuery = true)	    
				*/
	@Query( value= " select case when COALESCE(j.catf,0) > 0 then 'F' "
							+ "			when COALESCE(j.cate,0) > 0 then 'E' "
							+ "			when COALESCE(j.catd,0) > 0 then 'D' "
							+ "			when COALESCE(j.catc,0) > 0 then 'C' "
							+ "			when COALESCE(j.catb,0) > 0 then 'B' "
							+ "			when COALESCE(j.cata,0) > 0 then 'A'  "
							+ "	   else 'X' end as categoria "
							+ " from   tablaescalasalarialjornales j "
							+ " where  'PJ'                       = ?1 "
							+ " and      camara                = ?2 "
							+ " and      antiguedaddesde = ?3 "
							+ " and      fechadesde          = ( select max(x.fechadesde) from tablaescalasalarialjornales x "
							+ " 					                            where  x.camara          = j.camara "
							+ " 					                            and      x.antiguedaddesde = j.antiguedaddesde "
							+ " 					                            and      x.fechadesde     <= ?4 ) "
							+ " UNION						   "
							+ " select case when COALESCE(j.catf,0) > 0 then 'F' "
							+ "			when COALESCE(j.cate,0) > 0 then 'E' "
							+ "			when COALESCE(j.catd,0) > 0 then 'D' "
							+ "			when COALESCE(j.catc,0) > 0 then 'C'  "
							+ "			when COALESCE(j.catb,0) > 0 then 'B'  "
							+ "			when COALESCE(j.cata,0) > 0 then 'A'  "
							+ "	        else 'X' end as categoria "
							+ " from   tablaescalasalarialsueldos j "
							+ " where  'PS'                      = ?1 "
							+ " and      camara                = ?2 "
							+ " and      antiguedaddesde = ?3  "
							+ " and      fechadesde          = ( select max(x.fechadesde) from tablaescalasalarialsueldos x "
							+ " 					                            where  x.camara                = j.camara "
							+ " 					                            and      x.antiguedaddesde = j.antiguedaddesde "
							+ " 					                            and      x.fechadesde        <= ?4 ) ", 
							nativeQuery = true)	  
	public String findMenorCategoriaVigente(String tipo, String camara, Integer antiguedad, LocalDate vigencia);

	public List<VEscalaSalarial> findByTipoAndCamaraAndCategoriaAndAntiguedadAndVigenciaBeforeOrderByVigenciaDesc(String tipo, String camara, String categoria, Integer antiguedad, LocalDate vigencia);
	 	
	@Query(value="select distinct camara, antiguedaddesde antiguedad from tablaescalasalarialjornales order by 1,2", nativeQuery=true)  
	public List<EscalaSalarialAntiguedadI> getAntiguedades();
	
}
