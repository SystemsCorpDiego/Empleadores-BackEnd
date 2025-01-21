package ar.ospim.empleadores.auth.usuario.app.validator;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.RoleUtils;
import ar.ospim.empleadores.auth.usuario.app.RolAsignado;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioSessionStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.UsuarioRolRepository;
import ar.ospim.empleadores.sgx.exceptions.PermissionDeniedException;

@Service
public class UsuarioAuthoritiesValidator {

	private final MessageSource messageSource;
	private final UsuarioSessionStorage userSessionStorage;

	private final Supplier<List<String>> loggedUserClaims;
	private final Function<Integer, List<String>> entityUserClaims;

	public UsuarioAuthoritiesValidator(
			UsuarioSessionStorage userSessionStorage,
			UsuarioRolRepository userRoleRepository,
			MessageSource messageSource
	) {
		this.userSessionStorage = userSessionStorage;
		this.loggedUserClaims = () -> toRoleName(userSessionStorage.getRolesAssigned());
		this.entityUserClaims = (Integer userId) -> toRoleName(userRoleRepository.getRoleAssignments(userId));
		this.messageSource = messageSource;
	}

	protected void assertLoggedUserOutrank(Integer entityUserId) {
		if (entityUserId == null) {
			String errorMsg = messageSource.getMessage("auth.PERMISSION_DENIED", null, new Locale("es"));
			throw new PermissionDeniedException(errorMsg);
		}
		assertLoggedUserOutrank(entityUserClaims.apply(entityUserId));
	}

	protected void assertLoggedUserOutrank(List<String> roleNames) {
		if (!RoleUtils.loggedUserHasHigherRank(loggedUserClaims.get(), roleNames)) {
			String errorMsg = messageSource.getMessage("auth.SIN_PERMISO", null, new Locale("es"));
			throw new PermissionDeniedException(errorMsg);
		}
	}

	public boolean isLoggedUserId(Integer id) {
		return userSessionStorage.getUserId().equals(id);
	}

	private static List<String> toRoleName(Stream<RolAsignado> assignments) {
		return assignments
				.map(assignment -> assignment.getDescripcion())
				.collect(Collectors.toList());
	}

	private static List<String> toRoleName(List<RolAsignado> assignments) {
		return toRoleName(assignments.stream());
	}
}
