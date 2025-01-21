package ar.ospim.empleadores.auth.usuario.app;

import org.springframework.security.core.GrantedAuthority;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class RoleAssignmentAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1348626128643704495L;
	
	
	@Getter
	private final RolAsignado rolAsignado;

	public RoleAssignmentAuthority(RolAsignado roleAssignment) {
		this.rolAsignado = roleAssignment;
	}

	@Override
	public String getAuthority() {
		return rolAsignado.getDescripcion();
	}

	public RolAsignado assignment() {
		return this.rolAsignado;
	}
}
