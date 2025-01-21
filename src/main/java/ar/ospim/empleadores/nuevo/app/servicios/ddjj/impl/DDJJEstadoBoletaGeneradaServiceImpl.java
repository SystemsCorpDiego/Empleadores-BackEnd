package ar.ospim.empleadores.nuevo.app.servicios.ddjj.impl;

import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJEstadoBoletaGeneradaService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJStorageEnumException;
import ar.ospim.empleadores.nuevo.infra.out.store.DDJJStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.DDJJEstadoEnum;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DDJJEstadoBoletaGeneradaServiceImpl implements DDJJEstadoBoletaGeneradaService {
	private final MessageSource messageSource;
	private final DDJJStorage storage;
	
	public void run(Integer ddjjId) {
		validar(ddjjId);
		storage.setEstado(ddjjId, DDJJEstadoEnum.BOLETA_GENERADA.getCodigo());
	}
	
	private void validar(Integer ddjjId) {
		if ( ddjjId == null ) {			
			String errorMsg = messageSource.getMessage(CommonEnumException.ID_NO_INFORMADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ID_NO_INFORMADO.name(), errorMsg	);			
		}
		
		Optional<DDJJBO> cons = storage.findById(ddjjId);
		if ( cons.isEmpty() ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), 
					String.format(errorMsg, ddjjId)	);			
		}
		
		if ( !cons.get().getEstado().equals( DDJJEstadoEnum.PRESENTADA.getCodigo() ) ) {
			String errorMsg = messageSource.getMessage(DDJJStorageEnumException.ESTADO_BG_ERROR.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(DDJJStorageEnumException.ESTADO_BG_ERROR.name(), errorMsg);			
		}
		
	}
}
