package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaService;
import ar.ospim.empleadores.nuevo.infra.out.store.ConvenioSeteoStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioSeteo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ConvenioSeteoServiceImpl implements ConvenioSeteoService {
	
	private final MessageSource messageSource;
	private final ConvenioSeteoStorage storage;
	private final EmpresaService empresaService;
	
	
	@Override
	public LocalDate getFechaPagoMaxima(String cuit) {
		ConvenioSeteo convenioSeteo = getVigentePorCuit( cuit );
		
		if ( convenioSeteo != null && convenioSeteo.getIntencionPagoDiasMax()!= null && convenioSeteo.getIntencionPagoDiasMax()>0) {
			LocalDate fMaxima = LocalDate.now();
			fMaxima = fMaxima.plusDays(convenioSeteo.getIntencionPagoDiasMax());
			return fMaxima;
		}
		return null;
	}
	
	@Override
	public Boolean validarFechaPago(String cuit, LocalDate fechaPago) {
		LocalDate fMaxima = getFechaPagoMaxima( cuit);
		if ( fMaxima != null ) {
			if ( fechaPago.isAfter(fMaxima)  ) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public ConvenioSeteo guardar(ConvenioSeteo reg) {
		if ( reg.getCuit()!=null && "".equals(reg.getCuit().trim()) ) {
			reg.setCuit(null);
		}
		validarNulos(reg);
		validarVigenciaSolapada(reg);
		validarCuit(reg);
		
		return storage.save(reg);
	}
	
	@Override
	public void borrar(Integer id) {
		storage.deleteById(id);
	}
	
	@Override
	public List<ConvenioSeteo> findVigentes(LocalDate periodo) {
		return storage.getVigentes(periodo);		
	}

	@Override
	public ConvenioSeteo get(Integer id) {
		return storage.get(id);				
	}

	@Override
	public List<ConvenioSeteo> getAll() {
		return storage.getAll();
	}
	
	public ConvenioSeteo getVigentePorCuit(String cuit) {
		
		Optional<ConvenioSeteo> cons = storage.findContenido(cuit, LocalDate.now() );
		if ( cons.isPresent() )
			return cons.get();
		
		cons = storage.findContenidoGeneral( LocalDate.now() );
		if ( cons.isPresent() )
			return cons.get();

		return null;
	}
	
	
	private void validarCuit(ConvenioSeteo reg) {
		if ( reg.getCuit() != null )
			empresaService.getEmpresa(reg.getCuit());
		
	}
	
	private void validarNulos(ConvenioSeteo reg) {
		if ( reg.getDesde() == null) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Vigencia Desde"));
		}
		if ( reg.getCuotas() == null || reg.getCuotas().equals(0) ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Cant. Cuotas"));
		}
			
	}
	
	private void validarVigenciaSolapada(ConvenioSeteo reg) {
		LocalDate vigencia =  reg.getDesde();		
		validarVigenciaSolapada(reg.getCuit(), vigencia, reg.getId());
		vigencia =  reg.getHasta();
		if ( vigencia == null)
			vigencia = LocalDate.now().plusYears(9000);
		validarVigenciaSolapada(reg.getCuit(), vigencia, reg.getId());
	}
	
	private void validarVigenciaSolapada(String cuit, LocalDate vigencia, Integer id) {
		Optional<ConvenioSeteo> cons;
		if ( cuit == null) {
			if ( id != null) {
				cons = storage.findContenido(vigencia, id);
			} else {
				cons = storage.findContenido(vigencia);
			}
		} else {
			if ( id != null) {
				cons = storage.findContenido(cuit, vigencia, id);
			} else {
				cons = storage.findContenido(cuit, vigencia);
			}
		}
		
		if ( cons.isPresent() ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.FECHA_RANGO_EXISTENTE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.FECHA_RANGO_EXISTENTE.name(), errorMsg);
		}
	}
	
}
