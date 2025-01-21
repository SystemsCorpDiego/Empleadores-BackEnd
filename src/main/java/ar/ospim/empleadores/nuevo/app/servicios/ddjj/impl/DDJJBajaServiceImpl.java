package ar.ospim.empleadores.nuevo.app.servicios.ddjj.impl;

import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.seguridad.UsuarioInfo;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJBajaService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJStorageEnumException;
import ar.ospim.empleadores.nuevo.infra.out.store.DDJJStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.DDJJEstadoEnum;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DDJJBajaServiceImpl implements DDJJBajaService {
	private final MessageSource messageSource;
	
	private final UsuarioInfo usuarioInfo;  
	private final DDJJStorage storage;
	
	@Override
	public void run(Integer ddjjId) {
		validar(ddjjId);
		storage.borrar(ddjjId);
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
		
		DDJJBO ddjj = cons.get();
		if ( !ddjj.getEstado().equals( DDJJEstadoEnum.PENDIENTE.getCodigo() )) {
			String errorMsg = messageSource.getMessage(DDJJStorageEnumException.ESTADO_PRESENTADA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(DDJJStorageEnumException.ESTADO_PRESENTADA.name(),  errorMsg);			
		}
		
		if ( !usuarioInfo.validarEmpresa(ddjj.getEmpresa().getId()) ) {
			String errorMsg = messageSource.getMessage(DDJJStorageEnumException.USUARIO_EMPRESA_DIFERENTE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(DDJJStorageEnumException.USUARIO_EMPRESA_DIFERENTE.name(),  errorMsg);			
		}
	}

}
