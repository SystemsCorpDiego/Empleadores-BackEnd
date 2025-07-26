package ar.ospim.empleadores.nuevo.app.servicios.afipinteres;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.AfipInteresBO;
import ar.ospim.empleadores.nuevo.infra.out.store.AfipInteresStorage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AfipInteresServiceImpl implements AfipInteresService {

	private final MessageSource messageSource;
	private final  AfipInteresStorage storage;	
	
	
	
	@Override
	public AfipInteresBO guardar(AfipInteresBO reg) {
		if ( reg.getDesde() == null) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "'Vigencia Desde' "));			
		}
		if ( reg.getIndice() == null) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "'Indice' "));			
		}
		
		validarVigenciaSolapada(reg);		
		return storage.save(reg);
	}
	
	public void borrar(Integer id) {
		if (id == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ID_NO_INFORMADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ID_NO_INFORMADO.name(), errorMsg);
		}		
		AfipInteresBO reg = storage.findById(id);
		if ( reg == null) {
			String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), String.format(errorMsg, id) );			
		}
		
		storage.deleteById(id);
	}
	
	public List<AfipInteresBO> consultar() {
		return storage.findAllByOrderByDesdeDesc();
	}
	
	public AfipInteresBO getContenido(LocalDate desde) {
		Optional<AfipInteresBO> cons = storage.findContenido(desde);
		if ( cons.isPresent() )
			return cons.get();
		return null;
	}
	

	private void validarVigenciaSolapada(AfipInteresBO reg) {
		LocalDate vigencia =  reg.getDesde();		
		validarVigenciaSolapada(vigencia, reg.getId());
		vigencia =  reg.getHasta();
		if ( vigencia == null)
			vigencia = LocalDate.now().plusYears(9000);
		validarVigenciaSolapada(vigencia, reg.getId());
	}
	
	private void validarVigenciaSolapada(LocalDate vigencia, Integer id) {
		Optional<AfipInteresBO> cons = storage.findContenido(vigencia);
		if ( cons.isPresent() ) {
			if ( id == null || !cons.get().getId().equals(id) ) {
				String errorMsg = messageSource.getMessage(CommonEnumException.FECHA_RANGO_EXISTENTE.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.FECHA_RANGO_EXISTENTE.name(), errorMsg);
			}
		}
	}

	@Override
	public BigDecimal calcularInteres(BigDecimal capital, LocalDate desde, LocalDate hasta) {
		
		return storage.calcularInteres(capital, desde, hasta);
		
	}
	
	
}
