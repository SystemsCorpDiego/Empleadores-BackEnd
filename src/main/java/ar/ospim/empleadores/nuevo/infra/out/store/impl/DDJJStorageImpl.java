package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.servicios.afiliado.AfiliadoEnumException;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleAfiliadoDto;
import ar.ospim.empleadores.nuevo.infra.out.store.DDJJStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.DDJJMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.AfiliadoRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DDJJEmpleadoAporteRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DDJJEmpleadoRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DDJJRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EmpresaDomicilioRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Afiliado;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DDJJ;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DDJJEmpleado;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DDJJEmpleadoAporte;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.BoletaPagoDDJJEmpleadosConsultaI;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.DDJJSecuencia;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.DDJJSecuenciaI;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.DDJJTotales;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.DDJJTotalesI;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class DDJJStorageImpl implements DDJJStorage {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final AfiliadoRepository afiliadoRepository;
	private final EmpresaDomicilioRepository empresaDomicilioRepository;
    private final DDJJRepository repository;
	private final DDJJEmpleadoRepository empleadoRepository;
	private final DDJJEmpleadoAporteRepository empleadoAporteRepository;
    private final DDJJMapper mapper;
    private final DateTimeProvider dateTimeProvider;
    private final MessageSource messageSource;
    
    public List<DDJJBoletaArmadoDetalleAfiliadoDto> empleadosPorAporte(Integer ddjjId, String aporteCodigo) {
    	List<BoletaPagoDDJJEmpleadosConsultaI> lst = repository.empleadosPorAporte(ddjjId, aporteCodigo);
    	return lst.stream().map(reg -> mapper.map( reg) ).collect(Collectors.toList());
    }
    
    @Override
	public Optional<Integer> getUltimoPresentado(Integer empresaId, LocalDate periodo) {
    	if ( periodo == null)
    		return repository.getUltimoPresentado(empresaId);    	
    	return repository.getUltimoPresentadoPeriodo(empresaId, periodo);    	
    }

    @Override
    public Optional<Integer> getUltimoIngresadoPeriodo(Integer id){
    	return repository.getUltimoIngresadoPeriodo(id);    	
    }
    
    @Override
    public Optional<Integer> getUltimoIngresadoPeriodo(Integer empresaId, LocalDate periodo) {
    	return repository.getUltimoIngresadoPeriodo(empresaId, periodo);    	
    }
    
	@Override
	public List<DDJJTotales> consultarTotales(Integer empresaId) {
		List<DDJJTotalesI> lst = repository.consultaTotales(empresaId);
		return lst.stream().map(reg -> mapper.map( reg) ).collect(Collectors.toList());
	}


	@Override
	public List<DDJJTotales> consultarTotales(Integer empresaId, LocalDate desde, LocalDate hasta) {
		
		List<DDJJTotalesI> lst = null;
		if ( desde == null && hasta == null ) {
			lst = repository.consultaTotales(empresaId);
		} else {
			String desdeFmt = null;			
			String hastaFmt = null;
			try {
				desdeFmt = dateTimeProvider.getPeriodoDesdeToStringSql(desde);
				hastaFmt = dateTimeProvider.getPeriodoHastaToStringSql(hasta);
			} catch ( Exception e2 ) {				
				String errorMsg = messageSource.getMessage(CommonEnumException.FECHA_FMT_INVALIDO.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.FECHA_FMT_INVALIDO.name(), errorMsg );
			}			
			lst = repository.consultaTotales(empresaId, desdeFmt, hastaFmt);
		}
		return lst.stream().map(reg -> mapper.map( reg) ).collect(Collectors.toList());
	} 
	
	@Override
	public List<DDJJTotales> consultarTotales(String cuit, LocalDate desde, LocalDate hasta) {
		List<DDJJTotalesI> lst = null;
		if ( desde == null && hasta == null ) {
			lst = repository.consultaTotales( cuit );
		} else {			
			String desdeFmt = null;			
			String hastaFmt = null;
			try {
				desdeFmt = dateTimeProvider.getDesdeToStringSql(desde);
				hastaFmt = dateTimeProvider.getHastaToStringSql(hasta);
			} catch ( Exception e2 ) {				
				String errorMsg = messageSource.getMessage(CommonEnumException.FECHA_FMT_INVALIDO.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.FECHA_FMT_INVALIDO.name(), errorMsg );
			}
			if ( cuit == null ) {
				lst = repository.consultaTotales(desdeFmt, hastaFmt );
			} else {
				lst = repository.consultaTotales(cuit, desdeFmt, hastaFmt );
			}
			
		}
		
		return lst.stream().map(reg -> mapper.map( reg) ).collect(Collectors.toList());		
	}
	
	@Override
	public List<DDJJBO> findByEmpresaIdAndPeriodo(Integer empresaId, LocalDate periodo) {
		List<DDJJ> lst = repository.findByEmpresaIdAndPeriodo(empresaId, periodo);
		
		return  mapper.map(lst);
	}
	
	@Override
	public  Optional<LocalDate> findUltimoPeriodoPresentada(Integer empresaId){
		return repository.findUltimoPeriodoPresentada(empresaId);
	}
	
	@Override
	public Optional<DDJJBO> findById(Integer id) {
		DDJJBO rta = null;
		
		Optional<DDJJ> reg= repository.findById(id);
		if ( reg.isPresent() ) {
			rta = mapper.map(reg.get());			
			rta.setFechaCreacion( reg.get().getCreatedOn().toLocalDate() );	
		}
		return Optional.ofNullable(rta);
	}

	@Override
	public DDJJBO guardar(DDJJBO reg) {				
		DDJJ ddjj = mapper.map(reg);		
		logger.debug( "DDJJStorage.guardar() : ddjj.getDdjjEmpleados().size " +  ddjj.getEmpleados().size() );
		
		completarEmpleadosFK(ddjj);				
		
		ddjj = guardar( ddjj );
		logger.debug( "DDJJStorage.guardar() : repository.save(ddjj) -  ddjj.getDdjjEmpleados().size" +  ddjj.getEmpleados().size() );
		
		reg = mapper.map(ddjj);		
		logger.debug( "reg: " + reg.toString() );
		logger.debug(  "DDJJStorage.guardar() : " + "FIN !!!" );
		return reg;
	}
	
	@Transactional
	public void borrar(Integer ddjjId) {
		Optional<DDJJ> reg = repository.findById(ddjjId);
		if ( reg.isPresent()  ) {
			DDJJ ddjj = reg.get();
			for(DDJJEmpleado empleado: ddjj.getEmpleados()) {
				empleadoAporteRepository.deleteByDDJJEmpleadoId(empleado.getId());
				empleadoRepository.deleteById(empleado.getId());
			}
			repository.delete(reg.get());
		}
	}
	
	private DDJJ guardar(DDJJ ddjj) {				
		if ( ddjj.getId() == null ) {
			ddjj = alta(ddjj); 
		} else {
			ddjj = actualizar(ddjj); 
		}
		return ddjj;
	}
	
	@Transactional
	private DDJJ alta(DDJJ ddjj) {
		
		actualizarGerarquias(ddjj);
		
		ddjj = repository.save(ddjj);
		for (DDJJEmpleado empleado:  ddjj.getEmpleados() ) {
			empleado = empleadoRepository.save(empleado);
			if ( empleado.getAportes()  != null ) {
				for (DDJJEmpleadoAporte aporte:  empleado.getAportes() ) {
					aporte = empleadoAporteRepository.save(aporte);
				}
			}
		}
		repository.flush();
		return ddjj;
	}
	
	@Transactional
	private DDJJ actualizar(DDJJ ddjj) {
		
		//Piso la Entidad de la base, con la nueva version 
		Optional<DDJJ> cons = repository.findById(ddjj.getId());
		if ( cons.isPresent() ) {
			DDJJ ddjjActu = cons.get();
			logger.debug( "DDJJStorage.guardar() :  ddjjActu.getDdjjEmpleados().size " +  ddjjActu.getEmpleados().size() );
			mapper.mapSinAuditoria(ddjjActu, ddjj);
			ddjj = ddjjActu;
			ddjj.setUpdatedOn(null);
			logger.debug( "DDJJStorage.guardar() :  ddjj.getDdjjEmpleados().size" +  ddjj.getEmpleados().size() );				
		}
			
		actualizarGerarquias(ddjj);
		
		for ( DDJJEmpleado regEmple : ddjj.getLstEmpleadoAporteBaja() ) {
			logger.debug("empleadoAporteRepository.deleteByDDJJEmpleadoId() => regEmple.getId(): " + regEmple.getId());
			empleadoAporteRepository.deleteByDDJJEmpleadoId(regEmple.getId());
		}
		for ( DDJJEmpleado regEmple : ddjj.getLstEmpleadoBaja() ) {
			logger.debug("empleadoRepository.deleteById() => regEmple.getId(): " + regEmple.getId());
			empleadoAporteRepository.deleteByDDJJEmpleadoId(regEmple.getId());
			empleadoRepository.deleteById(regEmple.getId());
		}
		ddjj = repository.save(ddjj);
		repository.flush();
		return ddjj;
	}

	@Override
	public Integer getSecuencia(Integer ddjjId, LocalDate periodo) {
		return repository.getSecuencia(ddjjId, periodo);
	}

	@Override
	public void presentar(Integer ddjjId, Integer secuencia) {
		repository.presentar(ddjjId, secuencia);
	}

	@Override
	public void setEstado(Integer ddjjId, String estado) {
		repository.setEstado(ddjjId, estado);
	}

	
	private void completarEmpleadosFK(DDJJ ddjj) {
		Optional<Afiliado> afiliadoCons; 
		Afiliado afiliado;
		for ( DDJJEmpleado empleado : ddjj.getEmpleados() ) {
			empleado.setDdjj(ddjj);
			
			afiliadoCons = afiliadoRepository.findById(empleado.getAfiliado().getId()); //afiliado = afiliadoRepository.getById(empleado.getAfiliado().getId());
			if ( afiliadoCons.isPresent()) {
				afiliado = afiliadoCons.get();
			} else {
				afiliado = empleado.getAfiliado();
				//debe tener apellido y nombre.
				if ( afiliado.getApellido() == null || afiliado.getNombre() == null ) {
					String errorMsg = messageSource.getMessage(AfiliadoEnumException.NOMBRES_OBLIGATORIO.getMsgKey(), null, new Locale("es"));
					throw new BusinessException(AfiliadoEnumException.NOMBRES_OBLIGATORIO.name(), errorMsg );
				}
				//Fuerzo Mayusculas para nombre y apellido 
				afiliado.setApellido( afiliado.getApellido().toUpperCase() ); 
				afiliado.setNombre( afiliado.getNombre().toUpperCase() ); 

				afiliado = afiliadoRepository.save(afiliado);
			}			
			empleado.setAfiliado(afiliado);
			
			if ( empleado.getEmpresaDomicilio() != null && empleado.getEmpresaDomicilio().getId() != null ) { 
				empleado.setEmpresaDomicilio( empresaDomicilioRepository.getById( empleado.getEmpresaDomicilio().getId() ));
			}  else {
				empleado.setEmpresaDomicilio( null );
			}			
		}		
	}
	
	private void actualizarGerarquias(DDJJ ddjj) {
		for ( DDJJEmpleado empleado: ddjj.getEmpleados() ) {
			empleado.setDdjj(ddjj);
			if ( empleado.getAportes()  != null ) {
				for ( DDJJEmpleadoAporte aporte: empleado.getAportes() ) {
					aporte.setDdjjEmpleado(empleado);
				}
			}
		}		
	}

	@Override
	public List<Integer> findPeriodoPendiente(Integer EmpresaId, LocalDate periodo) {
		return repository.findPeriodoPendiente(EmpresaId, periodo);
	}

	public List<DDJJSecuencia> consultarSecuenciasPosterioresEnElPeriodo(Integer empresaId, Integer ddjjId){
		List<DDJJSecuenciaI> lst = repository.getSecuenciasPosterioresEnElPeriodo(empresaId, ddjjId);
		return lst.stream().map(reg -> mapper.map( reg) ).collect(Collectors.toList());
	}
}
