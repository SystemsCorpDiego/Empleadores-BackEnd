package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;
import java.util.Locale;

import javax.persistence.EntityNotFoundException;

import org.springframework.context.MessageSource;
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
	public DeudaNominaMailConfigBO findFirst() {
		List<DeudaNominaMailConfig> lista = repository.findAll();
		if (lista.isEmpty())
			return null;
		return mapper.map(lista.get(0));
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

}
