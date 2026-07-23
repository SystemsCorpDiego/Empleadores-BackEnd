package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.DeudaNominaMailConfigBO;
import ar.ospim.empleadores.nuevo.infra.out.store.DeudaNominaMailConfigStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.DeudaNominaMailConfigMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DeudaNominaMailConfigRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNominaMailConfig;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeudaNominaMailConfigStorageImpl implements DeudaNominaMailConfigStorage {

	private final MessageSource messageSource;
	private final DeudaNominaMailConfigRepository repository;
	private final DeudaNominaMailConfigMapper mapper;

	
	@Override
	public Optional<LocalDate> getFechaProcesoVigente() {
		LocalDate aux = repository.getFechaProcesoVigente();
		return Optional.ofNullable(aux);
	}
	
	@Override
	public List<DeudaNominaMailConfigBO> findAll() {
		List<DeudaNominaMailConfig> lista = repository.findAll( Sort.by(Sort.Direction.DESC, "fechaProceso") );
		return mapper.map(lista);
	}
	
	@Override
	public Optional<DeudaNominaMailConfigBO> findVigente() {
		Optional<DeudaNominaMailConfig> cons = repository.findVigente();
		if (cons.isEmpty())
			return Optional.ofNullable( new DeudaNominaMailConfigBO() );
		
		return Optional.ofNullable(mapper.map(cons.get()));
	}

	@Override
	public DeudaNominaMailConfigBO save(DeudaNominaMailConfigBO regBO) {
		DeudaNominaMailConfig registro;

		if (regBO.getId() != null) {
			try {
				registro = repository.getById(regBO.getId());
				mapper.map(regBO, registro);
			} catch (EntityNotFoundException enf) {
				String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(),
						String.format(errorMsg, regBO.getId()));
			}
		} else {
			registro = mapper.map(regBO);
		}

		registro = repository.save(registro);
		return mapper.map(registro);
	}

	@Override
	public Optional<DeudaNominaMailConfigBO> findByFecha(LocalDate fechaProceso) {
		DeudaNominaMailConfigBO rta = null;
		
		Optional<DeudaNominaMailConfig> registro = repository.findByFechaProceso(fechaProceso);
		if ( registro.isEmpty() ) 
			return Optional.ofNullable(rta);

		rta = mapper.map(registro.get());
		
		return Optional.ofNullable(rta);		
	}
	
}
