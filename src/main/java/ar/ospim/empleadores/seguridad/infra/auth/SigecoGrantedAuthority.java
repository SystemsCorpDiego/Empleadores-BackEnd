package ar.ospim.empleadores.seguridad.infra.auth;

import ar.ospim.empleadores.auth.usuario.app.RolAsignado;
import ar.ospim.empleadores.auth.usuario.app.RoleAssignmentAuthority;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
/**
 * Esta clase adapta un RoleAssignment para que cumpla con la
 * interfaz GrantedAuthority. El contexto de seguridad guarda una
 * lista de las autoridades asociadas al usuario logeado.
 *
 */public class SigecoGrantedAuthority  extends RoleAssignmentAuthority {

		private static final long serialVersionUID = 8208223394015158547L;

		public SigecoGrantedAuthority(RolAsignado roleAssignment) {
			super(roleAssignment);
		}
		
}
