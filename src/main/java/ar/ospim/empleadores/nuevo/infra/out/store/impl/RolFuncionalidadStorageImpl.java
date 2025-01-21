package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.infra.out.store.RolFuncionalidadStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.RolFuncionalidadRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.RolFuncionalidad;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolFuncionalidadStorageImpl implements RolFuncionalidadStorage {
    private final Logger logger =  LoggerFactory.getLogger(getClass());
    private final MessageSource messageSource;
    private final RolFuncionalidadRepository repository;
	
	
	public RolFuncionalidad save(RolFuncionalidad regBO) {
		RolFuncionalidad registro = null;

		Optional<RolFuncionalidad> cons = repository.findByRolAndFuncionalidad(regBO.getRol(), regBO.getFuncionalidad());
		
		if ( cons.isPresent() ) {
			registro = cons.get();
			registro.setActivo(regBO.isActivo());
		} else {
			registro = new RolFuncionalidad();
			registro.setFuncionalidad(regBO.getFuncionalidad());
			registro.setRol( regBO.getRol());
			registro.setActivo(regBO.isActivo());
		}
		 
		registro = repository.save(registro);
		
		return registro;
	}
	
	public void deleteById(Integer id) {
		repository.deleteById( id );
	}
	
	public List<RolFuncionalidad> findAll() {
		List<RolFuncionalidad> consulta = repository.findAll();
		return consulta ; 
	}

	public List<RolFuncionalidad> findByRol(String rol) {
		List<RolFuncionalidad> consulta = repository.findByRol(rol);
		return consulta ;  
	}

	public Optional<RolFuncionalidad> findById(Integer id) {
		Optional<RolFuncionalidad> reg = repository.findById(id);
		if ( reg.isPresent() ) {
			return reg;
		} else {
			return Optional.of(null);
		}
	}
	
}
