package ar.ospim.empleadores.nuevo.app.servicios.empresa.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.strings.StringHelper;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.DeudaNominaMailConfigBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.DeudaNominaMailConfigService;
import ar.ospim.empleadores.nuevo.infra.out.store.DeudaNominaMailConfigStorage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeudaNominaMailConfigServiceImpl implements DeudaNominaMailConfigService {

	private final MessageSource messageSource;
	private final DateTimeProvider dtProvider;
	private final DeudaNominaMailConfigStorage storage;

	@Override
	public List<DeudaNominaMailConfigBO> consultar() {		   
		return storage.findAll();
	}

	@Override
	public Optional<DeudaNominaMailConfigBO> consultarVigente() {
		return storage.findVigente();
	}
	
	@Override
	public DeudaNominaMailConfigBO crear(DeudaNominaMailConfigBO reg) {
		validar(reg);
		return storage.save(reg);
	}

	@Override
	public DeudaNominaMailConfigBO actualizar(Long id, DeudaNominaMailConfigBO reg) {
		reg.setId(id);
		validar(reg);
		return storage.save(reg);
	}

	
	private void validar(DeudaNominaMailConfigBO reg) {
		String errorMsg = null;
		if (reg == null || StringHelper.isNullOrWhiteSpace(reg.getCuerpoMail()) || reg.getFechaProceso() == null ) {
			errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg,  "Fecha de Proceso y Cuerpo del Mail" ));
		}					
		
		Optional<DeudaNominaMailConfigBO> cons = storage.findByFecha(reg.getFechaProceso());		
		if (cons.isPresent()) {			
			if( reg.getId() == null || !reg.getId().equals(cons.get().getId())  ) {
				errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_DUPLICADO.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.REGISTRO_DUPLICADO.name(), String.format(errorMsg,  dtProvider.getDateToString(reg.getFechaProceso()) ));
			}
		}
	}
}
