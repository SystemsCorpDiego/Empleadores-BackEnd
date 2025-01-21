package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.ContactoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaContactoStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.EmpresaContactoMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EmpresaContactoRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.EmpresaContacto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaContactoStorageImpl implements EmpresaContactoStorage {
	
	private final MessageSource messageSource;
    private final EmpresaContactoRepository repository;
    private final EmpresaContactoMapper mapper;
    
	public List<ContactoBO> findAll(Integer empresaId) {
		List<EmpresaContacto> consulta = repository.findByEmpresaId(empresaId);
		return mapper.map(consulta); 
	}


	@Override
	public Optional<ContactoBO> findById(Integer id) {
		Optional<EmpresaContacto>  consulta = repository.findById(id);
		if (consulta.isPresent()) {
			ContactoBO empresaContactoBO = mapper.map( consulta.get() );
			return Optional.of(empresaContactoBO);
		}
		Optional<ContactoBO> empty = Optional.empty();
		return  empty; 
	}
	
	@Override
	public List<ContactoBO> findByEmpresaId(Integer empresaId) {
		List<EmpresaContacto> cons =  repository.findByEmpresaId(empresaId);
		return mapper.map(cons);
	}
	
	@Override
	public List<ContactoBO> findByEmpresaIdAndTipo(Integer empresaId, String tipo) {
		// TODO Auto-generated method stub
		List<EmpresaContacto> cons = repository.findByEmpresaIdAndTipo(empresaId, tipo);
		return mapper.map(cons);
	}

	
	@Override
	public Optional<ContactoBO> findByEmpresaIdAndId(Integer empresaId, Integer id) {
		Optional<EmpresaContacto> consulta = repository.findById(id);
		if (consulta.isPresent()) {
			if ( consulta.get().getEmpresaId().equals(empresaId) ) {
				ContactoBO empresaContactoBO = mapper.map( consulta.get() );
				return Optional.of(empresaContactoBO);
			}
		}
		Optional<ContactoBO> empty = Optional.empty();
		return  empty; 
	}

	public ContactoBO save(Integer empresaId, ContactoBO regBO) { 
		EmpresaContacto registro;

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

	public void deleteById(Integer contactoId) {
		repository.deleteById( contactoId );
	}

 

	
}
