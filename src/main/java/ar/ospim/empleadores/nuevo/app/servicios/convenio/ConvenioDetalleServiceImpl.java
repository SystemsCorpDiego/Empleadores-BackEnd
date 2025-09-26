package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ActaMolinerosRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.AjusteRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioActaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioAjusteRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioPeriodoDetalleRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DeudaNominaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioActa;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioAjuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioPeriodoDetalle;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNomina;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ConvenioDetalleServiceImpl implements ConvenioDetalleService {

	private final ActaMolinerosRepository actaMolinerosRepository;
	private final ConvenioActaRepository convenioActaRepository;	
	private final ConvenioAjusteRepository convenioAjusteRepository;
	private final AjusteRepository ajusteRepository;
	private final ConvenioPeriodoDetalleRepository convenioPeriodoDetalleRepository;
	private final DeudaNominaRepository deudaNominaRepository;
	private final DateTimeProvider dateTimeProvider;
	
	@Override
	public Convenio runActas(Convenio convenio, List<Integer> dto) {
		boolean esAlta = false;
		if ( convenio.getId() == null) 
			esAlta = true;
		
		//Armado de ACTAS
		boolean existe;
		List<ConvenioActa> detaNew = new ArrayList<ConvenioActa>();
		
		if ( convenio.getActas() == null ) 
			convenio.setActas( new ArrayList<ConvenioActa>() );
		List<ConvenioActa> deta = convenio.getActas();
		
		ConvenioActa reg = null;
		
		//Altas de Acta
		for(Integer regDto : dto) {
			existe = false;
			for(ConvenioActa regDeta : deta) {
				if( regDeta.getId().equals(regDto) )
						existe = true;
			}
			if( !existe ) { //alta
				reg = new ConvenioActa(); 
				reg.setConvenio(convenio);
				reg.setActa( actaMolinerosRepository.getById(regDto) );
				detaNew.add(  reg );	
	
				if ( !esAlta )
					convenioActaRepository.save(reg);
			}
		 }
		
		//Baja de Acta
		for(ConvenioActa regDeta : deta) {
			existe = false;
			for(Integer regDto : dto) {
				if( regDeta.getId().equals(regDto) )
					existe = true;
			}
			if( !existe ) { //baja
				convenioActaRepository.deleteById(regDeta.getId());				
			} else {
				detaNew.add(  regDeta );	
			}
		}
		
		convenio.setActas(detaNew);		 		
		return convenio;
	}

	@Override
	public Convenio runActasGrabar(Convenio convenio) {
		List<ConvenioActa> deta = convenio.getActas();
		for(ConvenioActa regDeta : deta) {
			convenioActaRepository.save(regDeta);
		}
		return convenio;
	}
	
	@Override
	public Convenio runAjustes(Convenio convenio, List<Integer> dto) {
		boolean esAlta = false;
		if ( convenio.getId() == null ) 
			esAlta = true;
		
		boolean existe;
		List<ConvenioAjuste> detaNew = new ArrayList<ConvenioAjuste>();
		
		if ( convenio.getAjustes() == null ) 
			convenio.setAjustes( new ArrayList<ConvenioAjuste>() );
		List<ConvenioAjuste> deta = convenio.getAjustes();
			
		ConvenioAjuste reg = null;
		
		//Altas de Acta
		for(Integer regDto : dto) {
			existe = false;
			for(ConvenioAjuste regDeta : deta) {
				if( regDeta.getId().equals(regDto) )
						existe = true;
			}
			if( !existe ) { //alta
				reg = new ConvenioAjuste(); 
				reg.setConvenio(convenio);
				reg.setAjuste( ajusteRepository.getById(regDto) );
				
				BigDecimal importeUtilizado = ajusteRepository.importeUsado(regDto);			 
				reg.setImporte(BigDecimal.ZERO);
				reg.setImporte( reg.getAjuste().getImporte().add(importeUtilizado) ) ;
				reg.setImporte( reg.getImporte().multiply(BigDecimal.valueOf(-1)) );
				  
				detaNew.add(  reg );	

				if( !esAlta )
					convenioAjusteRepository.save(reg);
			}
		 }
		
		//Baja de Acta
		for(ConvenioAjuste regDeta : deta) {
			existe = false;
			for(Integer regDto : dto) {
				if( regDeta.getId().equals(regDto) )
					existe = true;
			}
			if( !existe ) { //baja
				convenioAjusteRepository.deleteById(regDeta.getId());				
			} else {
				detaNew.add(  regDeta );	
			}
		}
		convenio.setAjustes(detaNew);		
		
		return convenio;
	}


	@Override
	public Convenio runAjustesGrabar(Convenio convenio) {
		List<ConvenioAjuste> deta = convenio.getAjustes();
		for(ConvenioAjuste regDeta : deta) {
			convenioAjusteRepository.save(regDeta);
		}
		return convenio;
	}
	
	@Override
	public Convenio runPeriodos(Convenio convenio, List<String> dto) {
		boolean esAlta = false;
		if ( convenio.getId() == null ) 
			esAlta = true;
		
		boolean existe;
		List<ConvenioPeriodoDetalle> detaNew = new ArrayList<ConvenioPeriodoDetalle>();		
		
		if ( convenio.getPeriodos() == null ) 
			convenio.setPeriodos( new ArrayList<ConvenioPeriodoDetalle>() );
		List<ConvenioPeriodoDetalle> deta = convenio.getPeriodos();

		ConvenioPeriodoDetalle reg = null;
		DeudaNomina dn;
		
		//Altas de Periodos
		for(String regDto : dto) {
			existe = false;
			if (deta!=null) {
				for(ConvenioPeriodoDetalle regDeta : deta) {
					if( detallePeriodosEquals(regDeta, regDto) )
							existe = true;
				}
			}
			if( !existe ) { //alta				
				reg = new ConvenioPeriodoDetalle(); 
				reg.setConvenio(convenio);
				
				dn = getDetallePeriodos(convenio, regDto);
				reg.setPeriodo( dn.getPeriodo() );
				reg.setAporte(dn.getAporte().getCodigo());
				
				reg.setImporte( dn.getImporte() );
				reg.setInteres( dn.getInteres() );
				reg.setPago( dn.getAportePago() );
				
				reg.setDdjjId( dn.getDdjjId() );
				reg.setBoletaId( dn.getBoletaId() );
				reg.setDeudaNominaId( dn.getId() );				
				
				detaNew.add( reg );						
				
				if( !esAlta)
					convenioPeriodoDetalleRepository.save(reg); 
			}
		 }
		
		//Baja de Periodos
		for(ConvenioPeriodoDetalle regDeta : deta) {
			existe = false;
			for(String regDto : dto) {
				if( detallePeriodosEquals(regDeta, regDto) )
					existe = true;
			}
			if( !existe ) { //baja
				convenioPeriodoDetalleRepository.deleteById(regDeta.getId());				
			} else {
				detaNew.add( regDeta );	
			}
		}
		
		convenio.setPeriodos(detaNew);		
		
		return convenio;
	}

	@Override
	public Convenio runPeriodosGrabar(Convenio convenio) {
		List<ConvenioPeriodoDetalle> deta = convenio.getPeriodos();
		for(ConvenioPeriodoDetalle regDeta : deta) {
			convenioPeriodoDetalleRepository.save(regDeta);
		}
		return convenio;
	}
	
	public Convenio runGrabar(Convenio convenio) {
		convenio = runPeriodosGrabar(convenio);
		convenio = runAjustesGrabar(convenio);
		convenio = runActasGrabar(convenio);
		return convenio;
	}
	
	private Boolean detallePeriodosEquals(ConvenioPeriodoDetalle reg, String dto) {
		LocalDate regPeriodo;
		String regAporte;
		regPeriodo = dateTimeProvider.getDateBEP( dto.substring(0,8) );
		regAporte = dto.substring(8); 
		if ( regPeriodo.equals(reg.getPeriodo()) && regAporte.equals(reg.getAporte() )  )  {
			return true;
		}
		return false;
	}

	private DeudaNomina getDetallePeriodos(Convenio convenio, String periodoDto) {
		 //1) parsear periodo y aporte
		LocalDate regPeriodo;
		String regAporte;
		regPeriodo = dateTimeProvider.getDateBEP( periodoDto.substring(0,8) );
		regAporte = periodoDto.substring(8); 
				 
		 //2) consultar deuda para entidad+cuit+periodo+aporte
		 //dn = deudaNominaRepository.getById( reg.longValue() );
		DeudaNomina dn = deudaNominaRepository.findByEntidadAndCuitAndPeriodoAndAporteCodigo(convenio.getEntidad(), convenio.getEmpresa().getCuit(), regPeriodo, regAporte);
		return dn;
	}
	
	
}
