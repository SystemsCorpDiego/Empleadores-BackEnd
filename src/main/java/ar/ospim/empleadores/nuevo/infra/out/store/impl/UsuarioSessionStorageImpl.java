package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.RolAsignado;
import ar.ospim.empleadores.auth.usuario.app.RoleAssignmentAuthority;
import ar.ospim.empleadores.comun.auth.usuario.SecurityContextUtils;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioSessionStorage;

@Service
public class UsuarioSessionStorageImpl implements UsuarioSessionStorage {
	@Override
	public Integer getUserId() {
		return SecurityContextUtils.getUserDetails().userId;
	}

	@Override
	public Stream<RolAsignado> getRolesAssigned() {
		return SecurityContextUtils.getAuthentication().getAuthorities().stream()
				.map(grantedAuthority -> (RoleAssignmentAuthority)grantedAuthority)
				.map(RoleAssignmentAuthority::assignment)
				.map(this::toRoleAssignmentBo);
	}

	private RolAsignado toRoleAssignmentBo(RolAsignado rolAsignado) {
		return new RolAsignado( rolAsignado.getId(),  rolAsignado.getDescripcion() );
	}
}
