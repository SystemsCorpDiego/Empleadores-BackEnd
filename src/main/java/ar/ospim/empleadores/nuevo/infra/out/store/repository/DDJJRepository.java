package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DDJJ;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.BoletaPagoDDJJEmpleadosConsultaI;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.DDJJSecuenciaI;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.DDJJTotalesI;

@Repository
public interface DDJJRepository extends JpaRepository<DDJJ, Integer> {

	@Query(value =" select id from ddjj  "
			+ " where empresa_id = ?1 "
			+ " and estado in ('BG', 'PR') "
			+ " order by periodo desc, secuencia desc "
			+ " LIMIT 1 "
			,
			nativeQuery = true)
	public Optional<Integer> getUltimoPresentado(Integer empresaId);
	
	@Query(value = " select max(d.id) from ddjj d "
			+ "	           join (select *  from ddjj d2 "
			+ "			           where id = ?1) t "
			+ "              on d.empresa_id = t.empresa_id "
			+ "              and d.periodo = t.periodo "
			, 
	nativeQuery = true)
	public Optional<Integer> getUltimoIngresadoPeriodo(Integer id);


	@Query(value =" select id from ddjj  "
			+ " where empresa_id = ?1 "
			+ " and periodo = ?2"
			+ " order by id desc "
			+ " LIMIT 1 "
			,
			nativeQuery = true)
	public Optional<Integer> getUltimoIngresadoPeriodo(Integer empresaId, LocalDate periodo);

	
	@Query(value =" select id from ddjj  "
			+ " where empresa_id = ?1 "
			+ " and estado in ('BG', 'PR') "
			+ " and to_date(to_char(periodo, 'YYYY/MM/DD'), 'YYYY/MM/DD') = ?2"
			+ " order by id desc "
			+ " LIMIT 1 "
			,
			nativeQuery = true)
	public Optional<Integer> getUltimoPresentadoPeriodo(Integer empresaId, LocalDate periodo);

	@Query(value =" select d.afiliado_cuil_titular cuil, a.apellido, a.nombre, d.remunerativo, ap.importe capital "
			+ " from   ddjj_deta d "
			+ "            join afiliado a on a.cuil_titular = d.afiliado_cuil_titular and a.inte=0 "
			+ "            join ddjj_deta_aporte ap on ap.ddjj_deta_id = d.id "
			+ " where  d.ddjj_id = ?1 "
			+ " and    ap.aporte = ?2 "
			,
			nativeQuery = true)
	public List<BoletaPagoDDJJEmpleadosConsultaI> empleadosPorAporte(Integer ddjjId, String aporteCondigo);
	
	@Query(value ="select d.id, d.estado, d.presentada_fecha as presentada, d.empresa_id, e.cuit, e.razon_social,  d.periodo, d.secuencia, "
			+ "	   a.aporte,  sum(a.importe) as importe, "
			+ "	   (select max(id) from ddjj d2 "
			+ "	    where d2.empresa_id = d.empresa_id "
			+ "	    and   d2.periodo = d.periodo "
			+ "	    and   d2.id >= d.id) as ddjjVigente "
			+ "from ddjj d "
			+ "	inner join empresa e on e.id = d.empresa_id  "
			+ "	inner join ddjj_deta dd on d.id = dd.ddjj_id  "
			+ "	left join ddjj_deta_aporte a on dd.id = a.ddjj_deta_id "
			+ "   left join aporte ap on ap.codigo = a.aporte "
			+ "group by d.id, d.estado, d.empresa_id, e.cuit, e.razon_social,  d.periodo, d.secuencia, ap.orden, a.aporte "
			+ "order by  d.periodo desc, e.razon_social, d.secuencia desc, ap.orden, a.aporte desc", 
			  nativeQuery = true)
	public List<DDJJTotalesI> consultaTotales();
	
	@Query(value ="select d.id, d.estado, d.presentada_fecha as presentada, d.empresa_id, e.cuit, e.razon_social,  d.periodo, d.secuencia, "
			+ "	   a.aporte,  sum(a.importe) as importe, "
			+ "	   (select max(id) from ddjj d2 "
			+ "	    where d2.empresa_id = d.empresa_id "
			+ "	    and   d2.periodo = d.periodo "
			+ "	    and   d2.id >= d.id) as ddjjVigente "
			+ " from ddjj d "
			+ "	inner join empresa e on e.id = d.empresa_id  "
			+ "	inner join ddjj_deta dd on d.id = dd.ddjj_id  "
			+ "	left join ddjj_deta_aporte a on dd.id = a.ddjj_deta_id "
			+ "   left join aporte ap on ap.codigo = a.aporte "
			+ " where d.empresa_id = ?1 "
			+ " group by d.id, d.estado, d.empresa_id, e.cuit, e.razon_social, d.periodo, d.secuencia, ap.orden, a.aporte "
			+ " order by d.periodo desc, e.razon_social, d.secuencia desc, ap.orden asc ", 
			  nativeQuery = true)
	public List<DDJJTotalesI> consultaTotales(Integer empresaId);
	
	@Query(value ="select d.id, d.estado, d.presentada_fecha as presentada, d.empresa_id, e.cuit, e.razon_social,  d.periodo, d.secuencia, "
			+ "	   a.aporte,  sum(a.importe) as importe, "
			+ "	   (select max(id) from ddjj d2 "
			+ "	    where d2.empresa_id = d.empresa_id "
			+ "	    and   d2.periodo = d.periodo "
			+ "	    and   d2.id >= d.id) as ddjjVigente "
			+ " from ddjj d "
			+ "	inner join empresa e on e.id = d.empresa_id  "
			+ "	inner join ddjj_deta dd on d.id = dd.ddjj_id  "
			+ "	left join ddjj_deta_aporte a on dd.id = a.ddjj_deta_id "
			+ "   left join aporte ap on ap.codigo = a.aporte "
			+ " where d.empresa_id = ?1 "
			+ "and d.periodo BETWEEN CAST(?2 AS TIMESTAMP)  and CAST(?3 AS TIMESTAMP)  "
			+ " group by d.id, d.estado, d.empresa_id, e.cuit, e.razon_social, d.periodo, d.secuencia, ap.orden, a.aporte "
			+ " order by d.periodo desc, e.razon_social, d.secuencia desc, ap.orden asc ", 
			  nativeQuery = true)
	public List<DDJJTotalesI> consultaTotales(Integer empresaId, String desde, String hasta);
	
	@Query(value ="select d.id, d.estado, d.presentada_fecha as presentada, d.empresa_id, e.cuit, e.razon_social,  d.periodo, d.secuencia, "
			+ "	   a.aporte,  sum(a.importe) as importe, "
			+ "	   (select max(id) from ddjj d2 "
			+ "	    where d2.empresa_id = d.empresa_id "
			+ "	    and   d2.periodo = d.periodo "
			+ "	    and   d2.id >= d.id) as ddjjVigente "
			+ "from ddjj d "
			+ "	inner join empresa e on e.id = d.empresa_id  "
			+ "	inner join ddjj_deta dd on d.id = dd.ddjj_id  "
			+ "	left join ddjj_deta_aporte a on dd.id = a.ddjj_deta_id "
			+ "   left join aporte ap on ap.codigo = a.aporte "
			+ "where e.cuit =  ?1 "
			+ "group by d.id, d.estado, d.empresa_id, e.cuit, e.razon_social,  d.periodo, d.secuencia, ap.orden, a.aporte "
			+ "order by d.periodo desc, e.razon_social, d.secuencia desc, ap.orden, a.aporte desc", 
			  nativeQuery = true)
	public List<DDJJTotalesI> consultaTotales(String cuit );
	
	
	@Query(value ="select d.id, d.estado, d.presentada_fecha as presentada, d.empresa_id, e.cuit, e.razon_social,  d.periodo, d.secuencia, "
			+ "	   a.aporte,  sum(a.importe) as importe, "
			+ "	   (select max(id) from ddjj d2 "
			+ "	    where d2.empresa_id = d.empresa_id "
			+ "	    and   d2.periodo = d.periodo "
			+ "	    and   d2.id >= d.id) as ddjjVigente "
			+ "from ddjj d "
			+ "	inner join empresa e on e.id = d.empresa_id  "
			+ "	inner join ddjj_deta dd on d.id = dd.ddjj_id  "
			+ "	left join ddjj_deta_aporte a on dd.id = a.ddjj_deta_id "
			+ "   left join aporte ap on ap.codigo = a.aporte "
			+ "where ( ?1 IS NULL OR e.cuit =  ?1 ) "
			+ "and d.periodo BETWEEN CAST(?2 AS TIMESTAMP)  and CAST(?3 AS TIMESTAMP)  "
			+ "group by d.id, d.estado, d.empresa_id, e.cuit, e.razon_social,  d.periodo, d.secuencia, ap.orden, a.aporte "
			+ "order by d.periodo desc, e.razon_social, d.secuencia desc, ap.orden, a.aporte desc", 
			  nativeQuery = true)
	public List<DDJJTotalesI> consultaTotales(String cuit, String desde, String hasta);

	@Query(value ="select d.id, d.estado, d.presentada_fecha as presentada, d.empresa_id, e.cuit, e.razon_social,  d.periodo, d.secuencia, "
			+ "	   a.aporte,  sum(a.importe) as importe, "
			+ "	   (select max(id) from ddjj d2 "
			+ "	    where d2.empresa_id = d.empresa_id "
			+ "	    and   d2.periodo = d.periodo "
			+ "	    and   d2.id >= d.id) as ddjjVigente "
			+ "from ddjj d "
			+ "	inner join empresa e on e.id = d.empresa_id  "
			+ "	inner join ddjj_deta dd on d.id = dd.ddjj_id  "
			+ "	left join ddjj_deta_aporte a on dd.id = a.ddjj_deta_id "
			+ "   left join aporte ap on ap.codigo = a.aporte "
			+ " where d.periodo BETWEEN CAST(?1 AS TIMESTAMP)  and CAST(?2 AS TIMESTAMP)  "
			+ "group by d.id, d.estado, d.empresa_id, e.cuit, e.razon_social,  d.periodo, d.secuencia, ap.orden, a.aporte "
			+ "order by d.periodo desc, e.razon_social, d.secuencia desc, ap.orden, a.aporte desc", 
			  nativeQuery = true)
	public List<DDJJTotalesI> consultaTotales(String desde, String hasta);

	
	@Query(value ="select d "
			+ "from DDJJ d "
			+ "where d.empresa.id = ?1 "
			+ "and     d.periodo = ?2 "
			+ "order by d.id")
	public List<DDJJ> findByEmpresaIdAndPeriodo(Integer EmpresaId, LocalDate periodo);
	
	
	@Query(value ="select max(periodo) "
			+ "from ddjj d "
			+ "where d.empresa_id = ?1 "
			+ "and    d.estado = 'PE' ", 
			  nativeQuery = true)
	public Optional<LocalDate> findUltimoPeriodoPresentada(Integer EmpresaId);
	
	@Query(value ="select id "
			+ "from ddjj d "
			+ "where d.empresa_id = ?1 "
			+ "and     d.periodo = ?2 "
			+ "and    d.estado = 'PE' ", 
			  nativeQuery = true)
	public List<Integer> findPeriodoPendiente(Integer EmpresaId, LocalDate periodo);
	
	
	@Query(value ="update ddjj d "
			+ " set estado = 'PR',  "			
			+ " presentada_fecha = NOW(), "
			+ " secuencia = ?2 "
			+ "where d.id = ?1 ", 
			  nativeQuery = true)
	@Modifying
	public void presentar(Integer id, Integer secuencia);

	@Query(value ="update ddjj d "
			+ " set estado = ?2  "			
			+ "where d.id = ?1 ", 
			  nativeQuery = true)
	@Modifying
	public void setEstado(Integer id, String estado);
	
	@Query(value ="select max(COALESCE(secuencia, -1) ) from ddjj d "
			+ "where d.empresa_id  = (select empresa_id from ddjj t where id = ?1) "
			+ "and periodo = ?2 ", 
			  nativeQuery = true)
	public Integer getSecuencia(Integer id, LocalDate periodo);

	@Query(value =" select secuencia FROM ddjj d "
						+ " where d.empresa_id = ?1 "
						+ " and   d.periodo = (select d2.periodo from ddjj d2 where id = ?2) "
						+ " and   id > ?2 "
						+ " order by 1 desc ", 
	  nativeQuery = true)
	public List<DDJJSecuenciaI> getSecuenciasPosterioresEnElPeriodo(Integer empresaId, Integer ddjjId);
	
}
