package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.RolBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInternoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.RolStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioPersonaStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.UsuarioPersonaMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.UsuarioPersonaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.UsuarioRolRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.UsuarioPersona;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.UsuarioRol;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioPersonaStorageImpl implements UsuarioPersonaStorage  {
	
	private final UsuarioPersonaMapper mapper;
	
	private final UsuarioPersonaRepository repository;
	private final UsuarioRolRepository usuarioRolRepository;
	private final RolStorage rolStorage;
	
	@Override
	public UsuarioInternoBO save(UsuarioInternoBO regBO) {
		UsuarioPersona usuarioPersona = mapper.map( regBO );
		usuarioPersona = repository.save(usuarioPersona);
		
		return mapper.map( usuarioPersona );
	}

	@Override
	public List<UsuarioInternoBO> findAllUsuarioInterno() {
		Optional<RolBO> rolBo = null;
		List<UsuarioInternoBO> lst = repository.findAllUsuarioInterno();
		for(UsuarioInternoBO reg: lst) {
			List<UsuarioRol> lstRol = usuarioRolRepository.findByUserId(reg.getId());
			if ( lstRol.size()>0) { 
				rolBo = rolStorage.findById(lstRol.get(0).getRoleId());
				if ( rolBo.isPresent())
					reg.setRol(rolBo.get());
			}
		}
		return lst;
	}
	
	public Optional<UsuarioInternoBO> findByApellidoAndNombre(String apellido, String nombre) {
		UsuarioInternoBO reg = null;
		Optional<UsuarioPersona> find = repository.findByApellidoAndNombre(apellido, nombre);
		if ( find.isPresent() ) {
			reg = mapper.map(find.get());
			return Optional.of( reg );
		}
		return Optional.empty();
	}
	
	public Optional<UsuarioInternoBO> findByEmail(String email) {
		UsuarioInternoBO reg = null;
		Optional<UsuarioPersona> find = repository.findByEmail(email);
		if ( find.isPresent() ) {
			reg = mapper.map(find.get());
			return Optional.of( reg );
		}
		return Optional.empty();
	}

	public Optional<UsuarioInternoBO> findByUsuarioId(Integer id) {
		UsuarioInternoBO reg = null;
		Optional<UsuarioPersona> find = repository.findByUsuarioId(id);
		if ( find.isPresent() ) {
			reg = mapper.map(find.get());
			return Optional.of( reg );
		}
		return Optional.empty();
	}
}
