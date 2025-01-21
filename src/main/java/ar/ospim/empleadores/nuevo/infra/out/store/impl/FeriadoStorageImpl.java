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
import ar.ospim.empleadores.nuevo.app.dominio.FeriadoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.FeriadoStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.FeriadoMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.FeriadoRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Feriado;

@Service
public class FeriadoStorageImpl implements FeriadoStorage {
	
	private MessageSource messageSource;
	    
    private final FeriadoRepository repository;
    private final FeriadoMapper mapper;
	
	public FeriadoStorageImpl(MessageSource messageSource, FeriadoRepository feriadoRepository, FeriadoMapper mapper) {
        this.	messageSource = messageSource;			
        this.repository = feriadoRepository;
        this.mapper = mapper;
    }
	
	public FeriadoBO save(FeriadoBO regBO) {
		Feriado registro;

		if ( regBO.getId() != null) {
			try {
				registro = repository.getById(regBO.getId());
				mapper.map(regBO, registro);
			} catch( EntityNotFoundException enf) {
				String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE.name(), errorMsg + " [id= " + regBO.getId() + "]" );
			} 			 
		} else {
			registro = mapper.map(regBO);
		}
		 
		registro = repository.save(registro);
		
		return mapper.map(registro);
	}
	
	public void deleteById(Integer feriadoId) {
		repository.deleteById( feriadoId );
	}
	
	public Optional<FeriadoBO> get(LocalDate fecha) {
		Optional<Feriado> dato = repository.findByFecha(fecha);
		Optional<FeriadoBO> rta = null;

		if ( dato.isEmpty() )
			return Optional.empty();
		
		rta = Optional.of(mapper.map(dato.get()));
		return rta;
	}
	
	public List<FeriadoBO> findAll() {
		List<Feriado> consulta = repository.findAll();
		return mapper.map(consulta); 
	}

	public List<FeriadoBO> find(LocalDate desde) {
		List<Feriado> consulta = repository.getFeriadosDesde(desde);
		return mapper.map(consulta); 
	}
	
	public List<FeriadoBO> find(LocalDate desde, LocalDate hasta) {
		List<Feriado> consulta = repository.getFeriados(desde, hasta);
		return mapper.map(consulta); 
	}
}
