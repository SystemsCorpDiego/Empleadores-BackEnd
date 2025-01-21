package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;
import java.util.Locale;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.PublicacionBO;
import ar.ospim.empleadores.nuevo.infra.out.store.PublicacionStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.PublicacionMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.PublicacionRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Publicacion;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicacionStorageImpl implements PublicacionStorage{
    private final Logger logger =  LoggerFactory.getLogger(getClass());
    private final MessageSource messageSource;
    private final PublicacionRepository repository;
    private final PublicacionMapper mapper;
	
	
	public PublicacionBO save(PublicacionBO regBO) {
		Publicacion registro;

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
	
	public void deleteById(Integer feriadoId) {
		repository.deleteById( feriadoId );
	}
	
	public List<PublicacionBO> findAll() {
		List<Publicacion> consulta = repository.findAll();
		return mapper.map(consulta); 
	}
}
