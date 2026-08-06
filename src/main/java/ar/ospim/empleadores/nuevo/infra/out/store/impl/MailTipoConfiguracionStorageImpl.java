package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.dominio.MailTipoConfiguracionBO;
import ar.ospim.empleadores.nuevo.infra.out.store.MailTipoConfiguracionStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.MailTipoConfiguracionMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.MailTipoConfiguracionRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.MailTipoConfiguracion;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailTipoConfiguracionStorageImpl implements MailTipoConfiguracionStorage {

	private final MessageSource messageSource;
	private final MailTipoConfiguracionRepository repository;
	private final MailTipoConfiguracionMapper mapper;

	/*
	@Override
	public Optional<Integer> getIdVigente(Integer mailId) {
		Integer aux = repository.getIdVigente(mailId);
		return Optional.ofNullable(aux); 
	}
	*/
	
	@Override
	public List<MailTipoConfiguracionBO> findAll() {
		List<MailTipoConfiguracion> lista = repository.findAll( Sort.by(Sort.Direction.DESC, "id") );
		return mapper.map(lista);
	}
	
	@Override
	public Optional<MailTipoConfiguracionBO> findVigente(Integer mailId) {
		Optional<MailTipoConfiguracion> cons = repository.findVigente(mailId);
		if (cons.isEmpty())
			return Optional.ofNullable( new MailTipoConfiguracionBO() );
		
		return Optional.ofNullable(mapper.map(cons.get()));
	}

	@Override
	public MailTipoConfiguracionBO save(MailTipoConfiguracionBO regBO) {
		MailTipoConfiguracion registro;

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
