package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.RolBO;
import ar.ospim.empleadores.nuevo.infra.out.store.RolStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.RolMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.FuncionalidadRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.RolFuncionalidadRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.RolRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Funcionalidad;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Rol;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.RolFuncionalidad;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolStorageImpl implements RolStorage {
    private final Logger logger =  LoggerFactory.getLogger(getClass());
    private final MessageSource messageSource;
    private final RolRepository repository;
    private final RolMapper mapper;
	private final FuncionalidadRepository funcionalidadRepository;
	private final RolFuncionalidadRepository rolFuncionalidadRepository;
	
	@Transactional
	public RolBO save(RolBO regBO) {
		Rol registro;

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

		//En las ALTAS, le asigno todas las funciones en falso.- 
		if ( regBO.getId() == null) {
			deshabilitarFuncionalidades(registro);
		}

		return mapper.map(registro);
	}
	
	@Transactional
	public void deleteById(Short id) {
		deleteRolFuncionalidades(id);		
		repository.deleteById( id );
	}
	
	public List<RolBO> findAll() {
		List<Rol> consulta = repository.findAll();
		return mapper.map(consulta); 
	}

	public Optional<RolBO> findById(Short id) {
		Optional<Rol> reg = repository.findById(id);
		if ( reg.isPresent() ) {
			return Optional.of( mapper.map(reg.get()) );
		} else {
			return Optional.of(null);
		}
	}
	
	public Optional<RolBO> findByDescripcion(String descripcion) {
		Optional<Rol> reg = repository.findByDescripcion(descripcion);
		if ( reg.isPresent() ) {
			return Optional.of( mapper.map(reg.get()) );
		} else {
			return Optional.empty();
		}
	}

	private void deshabilitarFuncionalidades(Rol rol) {
		List<Funcionalidad> funcLst = funcionalidadRepository.findAll();
		RolFuncionalidad rf = null;
		for ( Funcionalidad func : funcLst ) {
			rf = new RolFuncionalidad();
			rf.setFuncionalidad(func.getDescripcion());
			rf.setRol(rol.getDescripcion());
			rf.setActivo(false);
			rolFuncionalidadRepository.save(rf);
		}
	}
	private void deleteRolFuncionalidades(Short id) {
		Optional<Rol> rol = repository.findById(id);
		if ( rol.isPresent())
			rolFuncionalidadRepository.deleteByRol(rol.get().getDescripcion());
	}
}
