package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaRestringidaBO;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaRestringidaStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.EmpresaRestringidaMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EmpresaRestringidaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.EmpresaRestringida;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaRestringidaStorageImpl implements EmpresaRestringidaStorage{
    private final MessageSource messageSource;
    private final EmpresaRestringidaRepository repository;
    private final EmpresaRestringidaMapper mapper;
	
	
	public EmpresaRestringidaBO save(EmpresaRestringidaBO regBO) {
		EmpresaRestringida registro;

		if ( regBO.getId() != null) {
			try {
				registro = repository.getById(regBO.getId());
				mapper.map(regBO, registro);
			} catch( EntityNotFoundException enf) {
				String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), 
						String.format(errorMsg, regBO.getId())  );
			} 			 
		} else {
			registro = mapper.map(regBO);
		}
		 
		registro = repository.save(registro);
		
		return mapper.map(registro);
	}
	
	public void deleteById(Integer id) {
		repository.deleteById( id );
	}
	
	public List<EmpresaRestringidaBO> findAll() {
		List<EmpresaRestringida> consulta = repository.findAll();
		
		return mapper.map(consulta); 
	}

	@Override
	public Optional<EmpresaRestringidaBO> findByCuit(String cuit) {
		Optional<EmpresaRestringida> cons = repository.findByCuit(cuit);
		if ( cons.isEmpty())
			return Optional.empty();
		
		return Optional.of( mapper.map(cons.get()) );
	}
}
