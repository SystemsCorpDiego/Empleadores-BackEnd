package ar.ospim.empleadores.auth.jwt.infra.input.rest;

import java.util.Optional;

import org.springframework.security.core.Authentication;

public interface AuthenticationExternalService {

	Optional<Authentication> getAppAuthentication(String username);

}
