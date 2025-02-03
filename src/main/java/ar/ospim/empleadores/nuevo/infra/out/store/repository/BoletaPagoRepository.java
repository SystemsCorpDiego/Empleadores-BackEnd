package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.BoletaPago;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.BoletaPagoDDJJConsultaI;

@Repository
public interface BoletaPagoRepository extends JpaRepository<BoletaPago, Integer>  {

	@Transactional
	@Modifying
    @Query(value="update boleta_Pago  set impresion = now() where id = ?1", nativeQuery=true)    
	public void registrarImpresion(Integer id);
	
	@Transactional
	@Modifying
    @Query(value="update boleta_Pago  set baja_en= now() where id = ?1", nativeQuery=true)    
	public void registrarBaja(Integer id);

	
	@Query(value="select COALESCE(max(secuencia),0)+1 from boleta_pago where empresa_id = ?1 ", nativeQuery=true)
	public Integer getSecuenciaProx(Integer empresaId);
	
	public List<BoletaPago> findByDdjjId(Integer ddjjId);
	
	@Query(value="select b from BoletaPago b where ddjjId IS NULL AND b.empresa.id = ?1 ")
	public List<BoletaPago> findByEmpresaIdSinDDJJ(Integer empresaId);

	@Query(value="select b from BoletaPago b where ddjjId IS NULL AND b.empresa.id = ?1 and b.intencionDePago between ?2 and ?3 ")
	public List<BoletaPago> findByEmpresaIdSinDDJJ(Integer empresaId, LocalDate desde,  LocalDate hasta);
	
	@Query(value="select b from BoletaPago b where ddjjId IS NOT NULL AND b.empresa.id = ?1 ")
	public List<BoletaPago> findByEmpresaIdConDDJJ(Integer empresaId);
	
	
	@Query(value="select b.id "
			+ " from boleta_pago b "
			+ "          inner join ddjj d on d.id = b.ddjj_id "
			+ "          inner join empresa e on e.id = d.empresa_id "
			+ " where d.periodo = ?2 "
			+ " and b.empresa_id = ?1 "
			+ " and b.baja_en is null "
			+ " and getboletapago_pagofecha_byid(e.cuit, b.secuencia) IS NULL ",
			 nativeQuery = true)
	public List<Integer> findByEmpresaIdAndPeriodoImpaga(Integer empresaId, LocalDate periodo);
	
	@Query(value ="select e.cuit, b.aporte, a.descripcion as aporteDescripcion, d.periodo, b.importe, b.interes, "
			+ "	   (select COALESCE(sum(bpa.importe),0) from boleta_pago_ajuste bpa where bpa.boleta_pago_id = b.id) ajuste, "			
			+ "      b.intencion_pago as intencionDePago, b.vencimiento, b.ddjj_id as ddjjId, b.secuencia as secuenciaBoleta, b.id, "
			+ "	   b.forma_pago as formaDePago, d.secuencia as secuenciaDdjj, b.bep, "
			+ "	   getboletapago_pagofecha(e.cuit, b.secuencia, a.entidad) as fechaDePago, "
			+ "	   getboletapago_pagoimporte(e.cuit, b.secuencia, a.entidad) as importeRecibido, "
			+ "	   b.baja_en as baja "
			+ "from boleta_pago b "
			+ "	inner join ddjj d on d.id = b.ddjj_id "
			+ "	inner join aporte a on a.codigo = b.aporte "
			+ "	inner join empresa e on e.id = b.empresa_id "
			+ "	where b.ddjj_id = ?1 "
			+ "order by d.periodo desc,  d.secuencia desc,  b.secuencia asc ", 
			  nativeQuery = true)
	public List<BoletaPagoDDJJConsultaI> consultaBoletaDDJJByDdjjId(Integer ddjjId);
	
	@Query(value ="select e.cuit, b.aporte, a.descripcion as aporteDescripcion, d.periodo, b.importe, b.interes, "
			+ "	   (select COALESCE(sum(bpa.importe),0) from boleta_pago_ajuste bpa where bpa.boleta_pago_id = b.id) ajuste, "			
			+ "      b.intencion_pago as intencionDePago, b.vencimiento, b.ddjj_id as ddjjId, b.secuencia as secuenciaBoleta, b.id, "
			+ "	   b.forma_pago as formaDePago, d.secuencia as secuenciaDdjj, b.bep, "
			+ "	   getboletapago_pagofecha(e.cuit, b.secuencia, a.entidad) as fechaDePago, "
			+ "	   getboletapago_pagoimporte(e.cuit, b.secuencia, a.entidad) as importeRecibido, "
			+ " from boleta_pago b "
			+ "	inner join ddjj d on d.id = b.ddjj_id "
			+ "	inner join aporte a on a.codigo = b.aporte "
			+ "	inner join empresa e on e.id = b.empresa_id "
			+ "	where b.id = ?1 "
			+ "  order by d.secuencia desc, b.secuencia desc ", 
			  nativeQuery = true)
	public BoletaPagoDDJJConsultaI consultaBoletaDDJJ(Integer boletaId);

	@Query(value ="select count(1)  from boleta_pago " 
			+ " where baja_en is null and ddjj_id = ?1 ", 
			nativeQuery = true)
	public Integer existeBoletaParaDDJJ(Integer ddjjId);

	@Query(value ="select e.id as empresaId, e.cuit, e.razon_social as razonSocial, b.aporte, a.descripcion as aporteDescripcion, d.periodo, b.importe, b.interes, "
			+ "	   (select COALESCE(sum(bpa.importe),0) from boleta_pago_ajuste bpa where bpa.boleta_pago_id = b.id) ajuste, "			
			+ "	   b.intencion_pago as intencionDePago, b.vencimiento, b.ddjj_id as ddjjId, b.secuencia as secuenciaBoleta, b.id, "
			+ "	   b.forma_pago as formaDePago, d.secuencia as secuenciaDdjj, b.bep, "
			+ "	   getboletapago_pagofecha(e.cuit, b.secuencia, a.entidad) as fechaDePago, "
			+ "	   getboletapago_pagoimporte(e.cuit, b.secuencia, a.entidad) as importeRecibido, "
			+ "	   b.baja_en as baja "
			+ "from boleta_pago b "
			+ "	   inner join ddjj d on d.id = b.ddjj_id "
			+ "	   inner join aporte a on a.codigo = b.aporte "
			+ "	   inner join empresa e on e.id = b.empresa_id "
			+ " where  "
			+ "           (d.periodo >= ?1 or cast(?1 as date) is null) "
			+ " and    (d.periodo <= ?2 or cast(?2 as date) is null) "
			+ " and    (b.aporte = cast(?3 as text) or cast(?3 as text)  is null) "
			+ " and    (a.entidad = cast(?4 as text) or cast(?4 as text)  is null) "
			+ " and    (b.forma_pago = cast(?5 as text) or cast(?5 as text)  is null) " //empresaId
			+ "order by d.periodo desc,  b.secuencia desc", 
			  nativeQuery = true)
	public List<BoletaPagoDDJJConsultaI> consultaBoletasDDJJ(LocalDate periodoDesde,  LocalDate periodoHasta, String aporte, String entidad, String formaPago );

	
	@Query(value ="select e.id as empresaId, e.cuit, e.razon_social as razonSocial, b.aporte, a.descripcion as aporteDescripcion, d.periodo, b.importe, b.interes, "
			+ "	   (select COALESCE(sum(bpa.importe),0) from boleta_pago_ajuste bpa where bpa.boleta_pago_id = b.id) ajuste, "			
			+ "	   b.intencion_pago as intencionDePago, b.vencimiento, b.ddjj_id as ddjjId, b.secuencia as secuenciaBoleta, b.id, "
			+ "	   b.forma_pago as formaDePago, d.secuencia as secuenciaDdjj, b.bep, "
			+ "	   getboletapago_pagofecha(e.cuit, b.secuencia, a.entidad) as fechaDePago, "
			+ "	   getboletapago_pagoimporte(e.cuit, b.secuencia, a.entidad) as importeRecibido, "
			+ "	   b.baja_en as baja "
			+ "from (select * from ddjj d where d.periodo >= ?1  and  d.periodo <= ?2 ) d  "
			+ "	   inner join boleta_pago b on d.id = b.ddjj_id "
			+ "	   inner join aporte a on a.codigo = b.aporte "
			+ "	   inner join empresa e on e.id = b.empresa_id "
			+ " where (b.aporte = cast(?3 as text) or cast(?3 as text)  is null) "
			+ " and    (a.entidad = cast(?4 as text) or cast(?4 as text)  is null) "
			+ " and    (b.forma_pago = cast(?5 as text) or cast(?5 as text)  is null) " //empresaId
			+ "order by d.periodo desc,  b.secuencia desc", 
			  nativeQuery = true)
	public List<BoletaPagoDDJJConsultaI> consultaBoletasDDJJPeriodoDH(LocalDate periodoDesde,  LocalDate periodoHasta, String aporte, String entidad, String formaPago);

	
	
	@Query(value ="select e.id as empresaId, e.cuit, e.razon_social as razonSocial, b.aporte, a.descripcion as aporteDescripcion, d.periodo, b.importe, b.interes, "
			+ "	   (select COALESCE(sum(bpa.importe),0) from boleta_pago_ajuste bpa where bpa.boleta_pago_id = b.id) ajuste, "			
			+ "	   b.intencion_pago as intencionDePago, b.vencimiento, b.ddjj_id as ddjjId, b.secuencia as secuenciaBoleta, b.id, "
			+ "	   b.forma_pago as formaDePago, d.secuencia as secuenciaDdjj, b.bep, "
			+ "	   getboletapago_pagofecha(e.cuit, b.secuencia, a.entidad) as fechaDePago, "
			+ "	   getboletapago_pagoimporte(e.cuit, b.secuencia, a.entidad) as importeRecibido, "
			+ "	   b.baja_en as baja "
			+ "from boleta_pago b "
			+ "	   inner join ddjj d on d.id = b.ddjj_id and d.empresa_id = b.empresa_id  "
			+ "	   inner join aporte a on a.codigo = b.aporte "
			+ "	   inner join empresa e on e.id = b.empresa_id and e.id  =  b.empresa_id  "
			+ " where b.empresa_id = cast(?6 as integer)  "
			+ " and    (d.periodo >= ?1 or cast(?1 as date) is null) "
			+ " and    (d.periodo <= ?2 or cast(?2 as date) is null) "
			+ " and    (b.aporte = cast(?3 as text) or cast(?3 as text)  is null) "
			+ " and    (a.entidad = cast(?4 as text) or cast(?4 as text)  is null) "
			+ " and    (b.forma_pago = cast(?5 as text) or cast(?5 as text)  is null) "  
			+ "order by d.periodo desc,  b.secuencia desc", 
			  nativeQuery = true)
	public List<BoletaPagoDDJJConsultaI> consultaBoletasDDJJEmpresa(LocalDate periodoDesde,  LocalDate periodoHasta, String aporte, String entidad, String formaPago, Integer empresaId);

}
