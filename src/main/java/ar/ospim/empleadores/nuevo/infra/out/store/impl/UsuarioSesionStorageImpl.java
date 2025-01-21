package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.RolAsignado;
import ar.ospim.empleadores.auth.usuario.app.RoleAssignmentAuthority;
import ar.ospim.empleadores.comun.auth.usuario.SecurityContextUtils;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioSesionStorage;

@Service
public class UsuarioSesionStorageImpl implements UsuarioSesionStorage {

	@Override
	public Integer getUsuarioId() {
		return SecurityContextUtils.getUserDetails().userId;
	}

	@Override
	public List<RolAsignado> getRolesAsignados() {
		return SecurityContextUtils.getAuthentication().getAuthorities().stream()
				.map(grantedAuthority -> (RoleAssignmentAuthority)grantedAuthority)
				.map(RoleAssignmentAuthority::assignment)
				.map(this::toRoleAssignmentBo).collect(Collectors.toList());
	}
	
	private RolAsignado toRoleAssignmentBo(RolAsignado rolAsignado) {
		return new RolAsignado(
				rolAsignado.getId(), rolAsignado.getDescripcion() 
		);
	}

}
