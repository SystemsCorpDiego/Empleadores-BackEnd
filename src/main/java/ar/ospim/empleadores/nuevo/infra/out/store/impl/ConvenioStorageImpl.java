package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioConsultaFiltroDto;
import ar.ospim.empleadores.nuevo.infra.out.store.ConvenioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioDdjjDeudaNominaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjj;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjjDeudaNomina;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.ConvenioDdjjDeudaNominaConsultaI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConvenioStorageImpl implements ConvenioStorage {

	 private final ConvenioRepository repository;
	 private final ConvenioDdjjDeudaNominaRepository convenioDdjjDeudaNominaRepository ; 
	 
	 @Transactional
	 @Override
	public Convenio guardar(Convenio reg) {
		Convenio regNew = repository.save(reg);
		return regNew;
	}

	 @Override
	 public Convenio getById(Integer id) {
		 return repository.getById(id);
	 }
	
	 private void imprimirconvenio(Convenio convenio) {
		 if ( convenio != null) {
			 log.debug( "imprimirconvenio() - convenio: " + convenio.toString() );
		 } else {
			 log.debug( "imprimirconvenio() - convenio: NULL");
		 }
	 }
	 
	 @Override
	 public Convenio get(Integer id) {
		
		Convenio convenio = repository.getById(id);
		imprimirconvenio(convenio);
		
		List<ConvenioDdjjDeudaNominaConsultaI> lst = null;
		List<ConvenioDdjjDeudaNomina> lst2 = null;
		ConvenioDdjjDeudaNomina aux = null;
		for( ConvenioDdjj reg : convenio.getDdjjs()) {
			lst = convenioDdjjDeudaNominaRepository.findDeudaNomina(reg.getId());
			if ( lst.size() > 0 ) {
				reg.setDdjjDeudaNomina(new ArrayList<ConvenioDdjjDeudaNomina>());
				
				for ( ConvenioDdjjDeudaNominaConsultaI regDN : lst) {
					aux = new ConvenioDdjjDeudaNomina();
					aux.setId( regDN.getId());
					aux.setAporte(regDN.getAporte());
					aux.setAporteImporte( regDN.getImporte());
					aux.setInteres( regDN.getInteres() );
					aux.setVencimiento( regDN.getVencimiento());
					aux.setBoletaId( regDN.getBoletaId() );
					reg.getDdjjDeudaNomina().add(aux);
				}
			}
			//log.error("test : lst" + lst.toString() );
		}		
		//imprimirconvenio(convenio);
		return convenio;		
	}

	@Override
	public List<Convenio> get(ConvenioConsultaFiltroDto filtro) {
		 
		if ( filtro.getDesde() == null &&  filtro.getHasta() == null ) {
			if ( filtro.getEmpresaId() != null && filtro.getEstado() != null ) {				
				return repository.findByEmpresaIdAndEstado(filtro.getEmpresaId(), filtro.getEstado());
			} else {
				if ( filtro.getEmpresaId() != null ) {
					return repository.findByEmpresaId(filtro.getEmpresaId());
				} else {
					if( filtro.getEstado() != null ) {		
						return repository.findByEstado(filtro.getEstado());
					} else {
						return null;
					}						
				}
			}
		} else {
			LocalDateTime localDateTimeDesde = null;
			LocalDateTime localDateTimeHasta = null;
			
			if (  filtro.getHasta() == null && filtro.getDesde() == null ) {
				filtro.setHasta( LocalDate.now() );
				filtro.setDesde( filtro.getHasta().minusMonths(11) );
			} else {
				if (  filtro.getHasta() == null ) {
					filtro.setHasta( filtro.getDesde().plusMonths(11) );
				} else {
					filtro.setDesde( filtro.getHasta().minusMonths(11) );
				}
			}			 
					
			localDateTimeDesde = filtro.getDesde().atTime(0,0, 0);
			localDateTimeHasta = filtro.getHasta().atTime(23,59, 59);
						
			if ( filtro.getEmpresaId() != null && filtro.getEstado() != null ) {				
				return repository.findByEmpresaIdAndEstadoAndCreatedOnBetween(filtro.getEmpresaId(), filtro.getEstado(), localDateTimeDesde, localDateTimeHasta);
			} else {
				if ( filtro.getEmpresaId() != null ) {
					return repository.findByEmpresaIdAndCreatedOnBetween(filtro.getEmpresaId(), localDateTimeDesde, localDateTimeHasta);
				} else {
					if ( filtro.getEstado() != null ) {		
						return repository.findByCreatedOnBetweenAndEstado(localDateTimeDesde, localDateTimeHasta, filtro.getEstado());
					} else {
						return null;
					}
				}
			}
		}				    		 
	}

	@Override
	public void actualizarImportes( Integer convenioId, BigDecimal p_imp_deuda, BigDecimal p_imp_interes, BigDecimal p_imp_saldo_favor ) {
		repository.actualizarImportes(convenioId, p_imp_deuda, p_imp_interes, p_imp_saldo_favor);
	}

	@Override
	public void actualizarModoPago( Integer convenioId, Integer p_cuotas_canti, LocalDate p_intencion_pago ) {
		repository.actualizarModoPago(convenioId, p_cuotas_canti, p_intencion_pago);
	}
	
}
