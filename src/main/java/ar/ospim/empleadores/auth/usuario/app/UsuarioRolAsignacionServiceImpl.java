package ar.ospim.empleadores.auth.usuario.app;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.servicios.auth.RolFuncionalidadService;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.rol.dto.FuncionalidadesDto;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.ERol;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.UsuarioRolRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.UsuarioRol;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.UsuarioRolPK;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioRolAsignacionServiceImpl implements UsuarioRolAsignacionService {

	private final UsuarioRolRepository usuarioRolRepository;
	private final RolFuncionalidadService rolFuncionalidadService;

	@Override
	public List<RolAsignado> getRolAsignado(Integer userId) {
		return usuarioRolRepository.getRoleAssignments(userId);
	}
	
	@Override
	public List<FuncionalidadesDto> getRolFuncionalidadActiva(String rol) {		
		return rolFuncionalidadService.getFuncionalidadesActivasByRol(rol);		
	}

	@Override
	public void saveUsuarioRol(Integer usuarioId, ERol eRol) {
		
		UsuarioRolPK pk = new UsuarioRolPK(usuarioId, eRol.getId());
		if (!usuarioRolRepository.findById(pk).isPresent()) {
			usuarioRolRepository.save(new UsuarioRol(pk.getUserId(), pk.getRoleId()));
		}
	}
	
	@Override
	public void saveUsuarioRol(Integer usuarioId, Short idRol) {
		
		UsuarioRolPK pk = new UsuarioRolPK(usuarioId, idRol);
		if (!usuarioRolRepository.findById(pk).isPresent()) {
			usuarioRolRepository.save(new UsuarioRol(pk.getUserId(), pk.getRoleId()));
		}
	}

	@Override
	public void removePermisos(Integer userId) {
		usuarioRolRepository.deleteByUserId(userId);
	}
	
	
}
