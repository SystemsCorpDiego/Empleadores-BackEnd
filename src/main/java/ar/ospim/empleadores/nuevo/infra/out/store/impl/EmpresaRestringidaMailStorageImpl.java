package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaRestringidaMailBO;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaRestringidaMailStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.EmpresaRestringidaMailMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EmpresaRestringidaMailRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.EmpresaRestringidaMail;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaRestringidaMailStorageImpl implements EmpresaRestringidaMailStorage {

	private final MessageSource messageSource;
	private final EmpresaRestringidaMailRepository repository;
	private final EmpresaRestringidaMailMapper mapper;

	@Override
	public EmpresaRestringidaMailBO save(EmpresaRestringidaMailBO regBO) {
		EmpresaRestringidaMail registro;

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
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public List<EmpresaRestringidaMailBO> findAll() {
		List<EmpresaRestringidaMail> consulta = repository.findAll();
		return mapper.map(consulta);
	}

	@Override
	public Optional<EmpresaRestringidaMailBO> findByCuit(String cuit) {
		Optional<EmpresaRestringidaMail> cons = repository.findByCuit(cuit);
		if (cons.isEmpty())
			return Optional.empty();
		return Optional.of(mapper.map(cons.get()));
	}
}
