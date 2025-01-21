package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AporteSeteo;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.AporteSeteoVigenteConsultaI;

public interface AporteSeteoRepository extends JpaRepository<AporteSeteo, Integer> {
	
	@Query(value =" select ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AporteSeteo(*)  FROM aporte_seteo a"
			+ " where exists ( select * FROM aporte_seteo s"
						+ "			   where  s.aporte = a.codigo"
						+ "			   and    s.desde < ?2 "
						+ "			   and    COALESCE(s.hasta, ?2 ) >= ?2 "
						+ "			  )", 
	nativeQuery = true)
	AporteSeteo getVigente(String codigo, LocalDate periodo);
	
	
	@Query(value =" select s.id, s.aporte, s.desde, s.hasta, s.entidad, s.socio, s.calculo_tipo as calculoTipo, s.calculo_valor as calculoValor, "
			+ "	    s.calculo_base as calculoBase, s.camara, s.camara_categoria as camaraCategoria, s.camara_antiguedad as camaraAntiguedad "
			+ " FROM aporte_seteo s, "
			+ "        ( select  s.aporte, max(s.desde) desde from aporte_seteo s"
			+ "          where s.desde <= ?1 and (s.hasta IS NULL OR s.hasta >= ?1 ) "
			+ "          group by 	s.aporte ) x "
			+ " where s.aporte = x.aporte "
			+ " and     s.desde  = x.desde ", 
	nativeQuery = true)
	List<AporteSeteoVigenteConsultaI> getVigentes(LocalDate periodo);
	
}
