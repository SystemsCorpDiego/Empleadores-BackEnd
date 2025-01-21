package ar.ospim.empleadores.auth;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ar.ospim.empleadores.nuevo.infra.out.store.enums.ERol;

public class RoleUtils {

	private RoleUtils() { }

	private static final List<String> CLAIMS_RANK = Stream.of(
			ERol.ROOT, // mayor rango, index 0
			ERol.ADMINISTRADOR
	).map(ERol::getValue).collect(Collectors.toList());

	public static boolean loggedUserHasHigherRank(List<String> loggedUserClaims, List<String> entityUserClaims) {
		int loggedUserRank = loggedUserClaims.stream()
				.filter(CLAIMS_RANK::contains)
				.map(CLAIMS_RANK::indexOf)
				.min(Integer::compareTo)
				.orElse(CLAIMS_RANK.size());
		int entityUserRank = entityUserClaims.stream()
				.filter(CLAIMS_RANK::contains)
				.map(CLAIMS_RANK::indexOf)
				.min(Integer::compareTo)
				.orElse(CLAIMS_RANK.size());
		return loggedUserRank < entityUserRank;
	}

}
