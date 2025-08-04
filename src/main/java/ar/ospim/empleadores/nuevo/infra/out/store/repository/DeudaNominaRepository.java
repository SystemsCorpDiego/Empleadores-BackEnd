package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IDeudaNominaDescargaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IGestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNomina;

public interface DeudaNominaRepository extends JpaRepository<DeudaNomina, Long> {
	
	@Query(value = "SELECT id, periodo, rectificativa, aporteCodigo, aporteDescripcion, importe, interes FROM public.fGestion_deuda_periodos_consul(:cuit)", nativeQuery = true)	
	List<IGestionDeudaDDJJDto> get(String cuit);

	@Query(value = "SELECT id, periodo, rectificativa, aporteCodigo, aporteDescripcion, importe, interes FROM public.fGestion_deuda_periodos_consul(:cuit, :entidad)", nativeQuery = true)	
	List<IGestionDeudaDDJJDto> get(String cuit, String entidad);
		
	@Query(value = "SELECT a FROM DeudaNomina a WHERE a.ddjjId = :ddjjId and a.actaId IS NULL and a.aporte.entidad = :entidad")	
	List<DeudaNomina> findByDdjjIdAndEntidadAndActaIdIsNull(Integer ddjjId, String entidad);
	
	
	@Query(value = "SELECT * FROM fdeudanomina_actualizar_x_log(?1);", nativeQuery = true)	
	void actualizarCuit( @Param("p_cuit")  String p_cuit);
	
	@Query(value = "select COALESCE( sum(ch.importe), 0) from convenio_cuota cu "
			+ "	join convenio_cuota_cheque ch on ch.convenio_cuota_id = cu.id "
			+ " where convenio_id = :convenioId ;", nativeQuery = true)
	BigDecimal countChequesImporteTotal(@Param("convenioId") Integer convenioId);
	
	//			+ " where d.aporte_importe+coalesce(d.interes,0) > coalesce( aporte_pago,0 ) "
	@Query(value = "select d.id, d.cuit, to_char(d.periodo, 'MM/YYYY') periodo, a.entidad, d.aporte, a.descripcion aporteDescripcion, "
			+ "       d.aporte_importe, to_char(d.vencimiento, 'dd/MM/YYYY')  vencimiento, d.interes, "
			+ "	   d.aporte_pago, to_char(d.aporte_pago_fecha_info, 'dd/MM/YYYY') aporte_pago_fecha_info, "
			+ "	   d.ddjj_id, dj.secuencia ddjj_secuencia, "
			+ "	   d.boleta_id, bp.secuencia boletaPago_secuencia, "
			+ "	   d.acta_id, d.acta_pago, (select numero from molineros_acta ma where ma.id = d.acta_id and a.entidad = fentidad_empleadores_get(ma.entidad) ) as acta_nro, "
			+ "	   d.convenio_id, d.convenio_pago, (select convenio_numero from molineros_convenio mc where mc.id = d.convenio_id and a.entidad = fentidad_empleadores_get(mc.entidad) ) as convenio_nro "
			+ " from   deuda_nomina d "
			+ "       join aporte a on a.codigo = d.aporte "
			+ "	   left outer join ddjj dj on dj.id  = d.ddjj_id "
			+ "	   left outer join boleta_pago bp on bp.id  = d.boleta_id "
			+ " where d.aporte_importe+coalesce(d.interes,0) > coalesce( aporte_pago,0 ) "
			+ "order by d.cuit, d.periodo desc, a.entidad, d.aporte", nativeQuery = true)
	List<IDeudaNominaDescargaDto> getDeudaNominaAll();
	 
	
}
