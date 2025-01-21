package ar.ospim.empleadores.nuevo.app.servicios.ddjj.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJConsultarService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJStorageEnumException;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleAfiliadoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJConsultaFiltroDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJPeriodoInfoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJTotalesAportesDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJTotalesEmpresaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.mapper.DDJJDtoMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.BoletaPagoStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.DDJJStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.DDJJEstadoEnum;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.DDJJSecuencia;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.DDJJTotales;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DDJJConsultarServiceImpl implements DDJJConsultarService {
	
	private final DDJJStorage storage;
	private final BoletaPagoStorage bpStorage;
	private final MessageSource messageSource;
	private final DDJJDtoMapper mapper;
	private final DateTimeProvider dtProvider;
	
	
	public DDJJPeriodoInfoDto consultarInfoPeriodo(Integer empresaId, LocalDate periodo) {
		DDJJPeriodoInfoDto rta = null;
		Optional<Integer> ddjjId = storage.getUltimoIngresadoPeriodo(empresaId, periodo);
		if ( ddjjId.isPresent() ) {
			Optional<DDJJBO> cons = storage.findById( ddjjId.get()  );
			if ( cons.isPresent() ) {
				rta = new DDJJPeriodoInfoDto();
				rta.setEstado(cons.get().getEstado());
				rta.setSecuencia(cons.get().getSecuencia());
				rta.setFechaCreacion( cons.get().getFechaCreacion() );
				rta.setFechaPresentacion( cons.get().getFechaPresentacion() );
				rta.setBoletaPago( bpStorage.existeBoletaParaDDJJ( cons.get().getId() ) );
			}
		}
		return rta;
	}
	
	public List<DDJJBoletaArmadoDetalleAfiliadoDto> empleadosPorAporte(Integer ddjjId, String aporteCodigo) {
		return storage.empleadosPorAporte(ddjjId, aporteCodigo);
	}
	
	public DDJJBO consultarPeriodoAnterior(Integer empresaId, LocalDate periodo) {
		Optional<DDJJBO> dto = null;
		Optional<Integer> ddjjId = storage.getUltimoPresentado(empresaId, periodo);
		if ( ddjjId.isEmpty() ) {
			//TODO: No existe presentacion para el periodo
			if( periodo != null ) {
				String errorMsg = messageSource.getMessage(DDJJStorageEnumException.PERIODO_ANTERIOR.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(DDJJStorageEnumException.PERIODO_ANTERIOR.name(), 
						String.format(errorMsg, dtProvider.getPeriodoToString(periodo) )	);			
			} else {
				String errorMsg = messageSource.getMessage(DDJJStorageEnumException.PERIODO_ANTERIOR_ULTIMO.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(DDJJStorageEnumException.PERIODO_ANTERIOR_ULTIMO.name(), errorMsg);			
			}
		}
		
		dto = storage.findById(ddjjId.get());
		if ( dto.isEmpty() ) {
			//ERROR: no se pudo recuperar el ID 
			String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), 
					String.format(errorMsg, ddjjId.get() )	);			
		}
		
		return dto.get();
	}
	
	public DDJJBO consultar(Integer id) {
		DDJJBO rta = null;
		Optional<DDJJBO> reg = storage.findById(id);
		
		if (reg.isEmpty()) {
			String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), 
					String.format(errorMsg, id)	);
		}
		rta = reg.get();
		
		//VALIDAR ESTADO de edicion !!!
		Optional<Integer> idUltimoIngresado = storage.getUltimoIngresadoPeriodo(id);
		
		if ( idUltimoIngresado.isPresent() &&  !idUltimoIngresado.get().equals(id) ) {
			rta.setEstado(DDJJEstadoEnum.NO_VIGENTE.getCodigo());
		}
		
		return rta;
	}

	public List<DDJJTotalesEmpresaDto> consultarTotales(DDJJConsultaFiltroDto filtro) {
		List<DDJJTotales> lst = null;
		if (filtro ==null || (filtro.getEmpresaId() == null && filtro.getCuit() == null && filtro.getDesde() == null && filtro.getHasta() == null ) ) {			
			String errorMsg = messageSource.getMessage(CommonEnumException.FILTRO_OBLIGATORIO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.FILTRO_OBLIGATORIO.name(), errorMsg);
		}
		if ( filtro.getEmpresaId() != null) {
			lst = storage.consultarTotales( filtro.getEmpresaId(), filtro.getDesde(), filtro.getHasta() );
		} else {
			lst = storage.consultarTotales( filtro.getCuit(), filtro.getDesde(), filtro.getHasta() );
		}
		return  cast(lst);
	}
	
	
	private List<DDJJTotalesEmpresaDto> cast(List<DDJJTotales> lst) {
		List<DDJJTotalesEmpresaDto> lstDto = new ArrayList<DDJJTotalesEmpresaDto>();
		int pos = -1;
		for (DDJJTotales reg: lst ) {
			DDJJTotalesEmpresaDto regNew = mapper.map(reg);
			DDJJTotalesAportesDto regAporte = mapper.mapAporte(reg);
			pos = lstDto.indexOf(regNew);			
			if(pos == -1) {
				regNew.setAportes( new ArrayList<DDJJTotalesAportesDto>() );
				regNew.getAportes().add(regAporte);
				lstDto.add(regNew);
			} else {
				lstDto.get(pos).getAportes().add(regAporte);
			}			
		}
		return  lstDto;
	}
	
	@Override
	public Optional<DDJJBO> getUltimaPresentada(Integer empresaId, LocalDate periodo) {
		if ( periodo == null) {
			Optional<LocalDate> find = storage.findUltimoPeriodoPresentada(empresaId);
			if ( find.isEmpty() )
				return Optional.empty();
			periodo = find.get();
		}
		
		List<DDJJBO> cons = storage.findByEmpresaIdAndPeriodo(empresaId, periodo);
		if ( cons.size() == 0 ) {
			return Optional.empty();
		}
		DDJJBO ddjj = null;
		for ( DDJJBO reg: cons) {
			if ( reg.getEstado().equals(DDJJEstadoEnum.PRESENTADA.getCodigo()) && 
					reg.getId() > ((ddjj!=null) ? ddjj.getId() : -1)
					) {
				ddjj = reg;
			}
		}
		if ( ddjj == null)
			return Optional.empty();
		
		return Optional.of(ddjj);
	}
	
	public Optional<Integer> getUltimoIngresadoPeriodo(Integer id) {
		return storage.getUltimoIngresadoPeriodo(id);
	}

	@Override
	public List<DDJJSecuencia> buscarSecuenciasPosterioresEnElPeriodo(Integer empresaId, Integer ddjjId) {
		return storage.consultarSecuenciasPosterioresEnElPeriodo(empresaId, ddjjId);		
	}
	
}
