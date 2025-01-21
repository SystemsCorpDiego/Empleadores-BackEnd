package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaDomicilioBO;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaDomicilioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.EmpresaDomicilioMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EmpresaDomicilioRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.EmpresaDomicilio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaDomicilioStorageImpl implements EmpresaDomicilioStorage {
	
	private final MessageSource messageSource;
    private final EmpresaDomicilioRepository repository;
    private final EmpresaDomicilioMapper mapper;
    
    
    public Integer domiDDJJCount(Integer domicilioId) {
    	return repository.domiDDJJCount(domicilioId);
    }
    
	public List<EmpresaDomicilioBO> findAll(Integer empresaId) {
		List<EmpresaDomicilio> consulta = repository.findByEmpresaId(empresaId);
		return mapper.map(consulta); 
	}

	public List<EmpresaDomicilioBO> findByEmpresaId(Integer empresaId) {
		List<EmpresaDomicilio> consulta = repository.findByEmpresaId(empresaId);
		return mapper.map( consulta );		
	}
	
	public Optional<EmpresaDomicilioBO> findByIdAndEmpresaId(Integer empresaId, Integer id) {
		Optional<EmpresaDomicilio> consulta = repository.findById(id);
		if (consulta.isPresent()) {
			if ( consulta.get().getEmpresaId().equals(empresaId) ) {
				EmpresaDomicilioBO empresaDomicilioBO = mapper.map( consulta.get() );
				return Optional.of(empresaDomicilioBO);
			}
		}
		Optional<EmpresaDomicilioBO> empty = Optional.empty();
		return  empty; 
	}

	public List<EmpresaDomicilioBO> findByEmpresaIdAndTipo(Integer empresaId, String tipo) {
		List<EmpresaDomicilio> consulta = repository.findByEmpresaIdAndTipo(empresaId, tipo);
		return mapper.map( consulta );		
	}
	
	public EmpresaDomicilioBO save(Integer empresaId, EmpresaDomicilioBO regBO) { 
		EmpresaDomicilio registro;

		if ( regBO.getId() != null) {
			try {
				registro = repository.getById(regBO.getId());
				mapper.map(registro, empresaId, regBO);
			} catch( EntityNotFoundException enf) {
				String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE.name(), errorMsg + " [id= " + regBO.getId() + "]" );
			} 			 
		} else {
			registro = mapper.map(empresaId, regBO);
		}

		registro = repository.save(registro);

		return mapper.map(registro);
	}

	public void deleteById(Integer domicilioId) {
		repository.deleteById( domicilioId );
	}
	
}
