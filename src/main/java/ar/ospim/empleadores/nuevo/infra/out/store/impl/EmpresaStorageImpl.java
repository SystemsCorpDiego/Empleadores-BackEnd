package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.exception.RepositoryException;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.EmpresaMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EmpresaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Empresa;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaStorageImpl implements EmpresaStorage {
	
	private final MessageSource messageSource;
    private final EmpresaRepository repository;
    private final EmpresaMapper mapper;
    
	@Override
	public EmpresaBO save(EmpresaBO regBO) {
		Empresa registro;

		if ( regBO.getId() != null) {
			try {
				Optional<Empresa> consulta = repository.findById(regBO.getId());
				if ( consulta.isEmpty() ) {
					String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
					throw new RepositoryException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), 
							String.format(errorMsg, regBO.getId())  );
				}
				registro = consulta.get();
				mapper.mapUpdate(registro, regBO); 
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
	
	
	public  Optional<EmpresaBO> findById(Integer id) {
		Optional<Empresa> reg = repository.findById(id);
		return  getEmpresaBO(reg);
	}

	public  Optional<EmpresaBO> findByCuit(String cuit){
		Optional<Empresa> reg = repository.findByCuit(cuit);
		return  getEmpresaBO(reg);
	}
	
	private Optional<EmpresaBO> getEmpresaBO(Optional<Empresa> reg) {
		if (reg.isEmpty() ) {
			Optional<EmpresaBO> empty = Optional.empty();
			return  empty;
		}
		EmpresaBO regBo = mapper.map( reg.get() );		
		return  Optional.of(regBo);
	}
}
