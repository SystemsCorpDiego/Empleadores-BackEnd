package ar.ospim.empleadores.auth.jwt.infra.input.rest;

import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.infra.input.servicio.SgxUserDetailsService;

@Service
public class AuthenticationExternalServiceImpl implements AuthenticationExternalService {

    private final SgxUserDetailsService sgxUserDetailsService;

    public AuthenticationExternalServiceImpl(SgxUserDetailsService sgxUserDetailsService) {
        this.sgxUserDetailsService = sgxUserDetailsService;
    }

    @Override
    public Optional<Authentication> getAppAuthentication(String username){
        return sgxUserDetailsService.loadSgxUserByUsername(username)
                .map(sgxUserDetails -> new UsernamePasswordAuthenticationToken(
                        sgxUserDetailsService.loadUserByUsername(username),
                        ""
                ));
    }
}
