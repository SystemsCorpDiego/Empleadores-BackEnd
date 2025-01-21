package ar.ospim.empleadores.nuevo.app.servicios.ddjj.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.seguridad.UsuarioInfo;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoDDJJImpagaBajaService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJPresentarService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJStorageEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJValidarPresentacion;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJPresentarResponseDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJValidarErrorDto;
import ar.ospim.empleadores.nuevo.infra.out.store.DDJJStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.DDJJEstadoEnum;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DDJJPresentarServiceImpl implements DDJJPresentarService {
	private final MessageSource messageSource;
	
	private final UsuarioInfo usuarioInfo;  
	private final DDJJStorage storage;
	private final BoletaPagoDDJJImpagaBajaService  boletaPagoDDJJImpagaBajaService;
	private final DDJJValidarPresentacion validarPresentacion; 

	@Override
	public DDJJPresentarResponseDto run(Integer ddjjId) {
		DDJJPresentarResponseDto rta = new DDJJPresentarResponseDto();
		DDJJBO  ddjj = validar(ddjjId) ;		
		Integer secuencia = storage.getSecuencia(ddjjId, ddjj.getPeriodo());
		secuencia = secuencia +1;
		storage.presentar(ddjjId, secuencia);
		
		boletaPagoDDJJImpagaBajaService.run(ddjj.getEmpresa().getId(), ddjj.getPeriodo());
		
		rta.setSecuencia(secuencia);
		rta.setEstado( DDJJEstadoEnum.PRESENTADA.getCodigo() );
		
		return rta;
	}

	private DDJJBO validar(Integer ddjjId) {
		
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
		
		Optional<List<DDJJValidarErrorDto>> validaciones = validarPresentacion.run(ddjj);
		if ( validaciones.isPresent() ) {
			if ( validaciones.get().size() > 0 ) {
				String errorMsg = messageSource.getMessage(DDJJStorageEnumException.PRESENTAR_VAL.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(DDJJStorageEnumException.PRESENTAR_VAL.name(),  errorMsg);			
			}
		}
		return ddjj;
	}
	
}
