package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoFiltroDto;
import ar.ospim.empleadores.nuevo.infra.out.store.BoletaPagoStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.BoletaPagoMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.AjusteRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.AporteRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.BoletaPagoAjusteRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.BoletaPagoRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EmpresaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Ajuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Aporte;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.BoletaPago;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.BoletaPagoAjuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Empresa;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.BoletaPagoDDJJConsulta;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.BoletaPagoDDJJConsultaI;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class BoletaPagoStorageImpl  implements BoletaPagoStorage {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final BoletaPagoRepository repository;
	private final BoletaPagoAjusteRepository boletaPagoAjusteRepository;
	private final EmpresaRepository empresaRepository;
	private final AporteRepository aporteRepository;
	private final AjusteRepository ajusteRepository;
	private final BoletaPagoMapper mapper;
	private final DateTimeProvider dtProvider; 
	
	@Override
	public Optional<BoletaPagoBO> findById(Integer id) {
		BoletaPagoBO rta = null;
		Optional<BoletaPago> reg = repository.findById(id);
		if ( reg.isPresent() )
			rta = mapper.map(reg.get());
		return Optional.ofNullable(rta); 
	}

	@Override
	public  List<BoletaPagoBO> findByDDJJId(Integer ddjjId) {		
		List<BoletaPago> lst = repository.findByDdjjId(ddjjId);
		return mapper.map(lst);
	}
	
	@Override
	public BoletaPagoBO guardar(BoletaPagoBO reg) {
		BoletaPago regNew = mapper.map(reg);
		completarFK(regNew);
		regNew = guardar( regNew );
		reg = mapper.map(regNew);		
		return reg;
	}
	
	private void completarFK(BoletaPago bp) {
		Optional<Ajuste> ajuste = null;
		if ( bp.getAjustes()  != null ) {
			for ( BoletaPagoAjuste bpAjuste : bp.getAjustes() ) {
				bpAjuste.setBoletaPago(bp);
				ajuste  = ajusteRepository.findById( bpAjuste.getAjuste().getId() );
				if ( ajuste.isPresent() ) {
					bpAjuste.setAjuste( ajuste.get() );
				}			
			}
		}
	}

	@Override
	public void borrar(Integer id) {
		repository.deleteById(id);
	}

	private BoletaPago guardar(BoletaPago reg) {				
		if ( reg.getId() == null ) {
			reg = alta(reg); 
		} else {
			reg = actualizar(reg); 
		}
		return reg;
	}
	
	@Transactional
	private BoletaPago alta(BoletaPago reg) {
		if ( reg.getEmpresa() != null && reg.getEmpresa().getId() != null ) {
			reg.setEmpresa( empresaRepository.getById( reg.getEmpresa().getId() ) );
		} else {
			reg.setEmpresa( null );
		}
		
		if ( reg.getAporte() != null && reg.getAporte().getCodigo() != null ) {
			Optional<Aporte> aporte = aporteRepository.getByCodigo( reg.getAporte().getCodigo() );
			if ( aporte.isPresent() ) {
				reg.setAporte( aporte.get() );
			}
		} else {
			reg.setAporte( null ); 
		}
		reg.setSecuencia( repository.getSecuenciaProx( reg.getEmpresa().getId() ) );
		
		reg = repository.save(reg);

		//Guardo el detalle de Ajustes
		if ( reg.getAjustes() != null) {
			for (BoletaPagoAjuste bpAjuste:  reg.getAjustes() ) {
				bpAjuste = boletaPagoAjusteRepository.save(bpAjuste);
			}
		}
		repository.flush();
		return reg;
	}
	
	@Transactional
	private BoletaPago actualizar(BoletaPago reg) {
		
		//Piso la Entidad de la base, con la nueva version 
		Optional<BoletaPago> cons = repository.findById(reg.getId());
		if ( cons.isPresent() ) {
			BoletaPago boletaPago = cons.get();
			//La empresa se ignora
			mapper.map(boletaPago, reg);
			
			if ( reg.getAporte() != null && reg.getAporte().getCodigo() != null ) {
				Optional<Aporte> aporte = aporteRepository.getByCodigo( reg.getAporte().getCodigo() );
				if ( aporte.isPresent() ) {
					reg.setAporte( aporte.get() );
				}
			} else {
				reg.setAporte(null); 
			}
			
			reg = boletaPago;
			//ddjj.setUpdatedOn(null);
			//logger.debug( "DDJJStorage.guardar() :  ddjj.getDdjjEmpleados().size" +  ddjj.getEmpleados().size() );				
		}
		 
		reg = repository.save(reg);
		repository.flush();
		return reg;
	}

	public  List<BoletaPagoBO> findByEmpresaIdSinDDJJ(Integer empresaId) {
		List<BoletaPago> cons = repository.findByEmpresaIdSinDDJJ(empresaId);
		return mapper.map(cons);
	}
	
	public  List<BoletaPagoBO> findByEmpresaIdConDDJJ(Integer empresaId) {
		List<BoletaPago> cons =  repository.findByEmpresaIdConDDJJ(empresaId);
		return mapper.map(cons);
	}
	
	@Override
	public List<BoletaPagoBO> findByEmpresaIdSinDDJJ(Integer empresaId, LocalDate desde, LocalDate hasta) {
		desde = dtProvider.getPeriodoDesde(desde);
		hasta = dtProvider.getPeriodoHasta(hasta);
		
		List<BoletaPago> cons =  repository.findByEmpresaIdSinDDJJ(empresaId, desde, hasta);
		return mapper.map(cons);
	}
	
	public List<BoletaPagoDDJJConsulta> consultarConDDJJ(BoletaPagoFiltroDto filtro) {
		List<BoletaPagoDDJJConsultaI> lst = null;
		LocalDate desde = dtProvider.getPeriodoDesde(filtro.getPeriodoDesde());
		LocalDate hasta = dtProvider.getPeriodoHasta(filtro.getPeriodoHasta());
		
		if ( filtro.getEmpresaId() == null ) {
			if ( filtro.getCuit() != null) {
				filtro.setEmpresaId( -2 );
				Optional<Empresa> cons = empresaRepository.findByCuit( filtro.getCuit() );
				if ( cons.isPresent() ) {				
					filtro.setEmpresaId( cons.get().getId() );
				}
			}
		}
		
		if ( filtro.getEmpresaId() != null ) {
			lst = repository.consultaBoletasDDJJEmpresa( desde, hasta, filtro.getConcepto(), filtro.getEntidad(), filtro.getFormaPago(), filtro.getEmpresaId() );
		} else {
			if ( desde != null && hasta !=null) {
				lst = repository.consultaBoletasDDJJPeriodoDH( desde, hasta, filtro.getConcepto(), filtro.getEntidad(), filtro.getFormaPago() );
			} else {
				lst = repository.consultaBoletasDDJJ( desde, hasta, filtro.getConcepto(), filtro.getEntidad(), filtro.getFormaPago()  );
			}
		}
		List<BoletaPagoDDJJConsulta> rta = lst.stream().map(reg -> mapper.map( reg ) ).collect(Collectors.toList());
		
		return rta;	
	}
	
    
    public List<BoletaPagoDDJJConsulta> consultarByDdjjId(Integer ddjjId) {
    	List<BoletaPagoDDJJConsultaI> lst = null;
    	lst = repository.consultaBoletaDDJJByDdjjId( ddjjId );
		List<BoletaPagoDDJJConsulta> rta = lst.stream().map(reg -> mapper.map( reg ) ).collect(Collectors.toList());
		 
		return rta;		
    }

    public BoletaPagoDDJJConsulta consultarConDDJJ(Integer boletaId) {
    	BoletaPagoDDJJConsultaI reg = null;
		
    	reg = repository.consultaBoletaDDJJ( boletaId );
		BoletaPagoDDJJConsulta rta = mapper.map( reg ) ;
		 
		return rta;
    }

    public Boolean existeBoletaConAjuste(Integer ajusteId) {
    	return boletaPagoAjusteRepository.existeBoletaConAjuste(ajusteId);
    }

    public Integer getSecuenciaProx(Integer empresaId) {
    	return repository.getSecuenciaProx(empresaId);
    }
    
    public Boolean existeBoletaParaDDJJ(Integer ddjjId) {    	
    	Integer aux = repository.existeBoletaParaDDJJ(ddjjId);
    	if (aux.equals(0))
    		return false;
    	return true;    	
    }

	@Override
	public void registrarImpresion(Integer boletaId) {
		repository.registrarImpresion(boletaId);
	}

	@Override
	public void registrarBaja(Integer boletaId) {
		repository.registrarBaja(boletaId);
	}
    
	@Override
	public List<Integer> findByEmpresaIdAndPeriodoImpaga(Integer empresaId, LocalDate periodo) {
		return repository.findByEmpresaIdAndPeriodoImpaga(empresaId, periodo);
	}
	
}
