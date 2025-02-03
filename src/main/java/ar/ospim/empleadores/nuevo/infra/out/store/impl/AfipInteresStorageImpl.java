package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.AfipInteresBO;
import ar.ospim.empleadores.nuevo.infra.out.store.AfipInteresStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.AfipInteresMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.AfipInteresRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AfipInteres;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AfipInteresStorageImpl implements AfipInteresStorage {
	private MessageSource messageSource;
	
    private final AfipInteresRepository repository;
    private final AfipInteresMapper mapper;
	
	public AfipInteresStorageImpl(MessageSource messageSource, AfipInteresRepository afipInteresRepository, AfipInteresMapper mapper) {
        this.	messageSource = messageSource;			
        this.repository = afipInteresRepository;
        this.mapper = mapper;
    }
	
	public Optional<AfipInteresBO> findByDesde(LocalDate desde) {
		Optional<AfipInteres> consulta = repository.findByDesde(desde);
		if ( consulta.isEmpty() ) {
			return Optional.ofNullable(null);
		}
		AfipInteresBO reg = mapper.map(consulta.get());		
		return Optional.of(reg);
	}
	
	public Optional<AfipInteresBO> findContenido(LocalDate desde) {
		Optional<AfipInteres> consulta = repository.findContenido(desde);
		if ( consulta.isEmpty() ) {
			return Optional.ofNullable(null);
		}
		AfipInteresBO reg = mapper.map(consulta.get());		
		return Optional.of(reg);
	}
	
	public AfipInteresBO save(AfipInteresBO regBO) {
		AfipInteres registro;

		if ( regBO.getId() != null) {
			try {			
				registro = repository.getById(regBO.getId());
				mapper.map(registro, regBO);
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
	
	public AfipInteresBO findById(Integer id) {
		AfipInteresBO reg = null;
		Optional<AfipInteres> cons = repository.findById( id );
		if (cons.isPresent() )
			reg = mapper.map(cons.get()); 
		
		return reg;
	}

	public List<AfipInteresBO> findAll() {
		List<AfipInteres> consulta = repository.findAll();
		return mapper.map(consulta); 
	}
	
	public List<AfipInteresBO> findAllByOrderByDesdeDesc() {
		List<AfipInteres> consulta = repository.findAllByOrderByDesdeDesc();
		return mapper.map(consulta);
	}
}
