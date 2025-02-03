package ar.ospim.empleadores.auth.usuario.app.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.dfa.app.ConfirmarDFA;
import ar.ospim.empleadores.auth.usuario.app.TokenGestionUsuario;
import ar.ospim.empleadores.auth.usuario.app.UsuarioActivarPorToken;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.RegistrarUsuarioEnumException;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioActivarPorTokenImpl implements UsuarioActivarPorToken {
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private TokenGestionUsuario tokenActivacionUsuario;
	
	@Autowired
	private ConfirmarDFA confirmarDFA;
	
	@Autowired
	private UsuarioStorage storage;

	
	@Override
	public String run(String token) {
		Integer usuarioId = tokenActivacionUsuario.getId(token);
		String usuario = tokenActivacionUsuario.getUsuario(token);
		String tokenDFA = tokenActivacionUsuario.getTokenDFA(token);
		
		UsuarioBO usuarioBO = storage.getUsuario(usuario);
		if ( !usuarioBO.getId().equals(usuarioId) ) {
			log.error("TokenActivacionUsuario.getId() - Error  - token: " + token);
			String errorMsg = messageSource.getMessage(RegistrarUsuarioEnumException.TOKEN_HABI.getMsgKey(), null, new Locale("es"));
            throw new BusinessException(RegistrarUsuarioEnumException.TOKEN_HABI.name(), errorMsg);			
		}
		
		if( usuarioBO.isHabilitado() ) {
			String errorMsg = messageSource.getMessage(RegistrarUsuarioEnumException.TOKEN_HABI.getMsgKey(), null, new Locale("es"));
            throw new BusinessException(RegistrarUsuarioEnumException.TOKEN_HABI.name(), errorMsg);			
		}
		usuarioBO.setHabilitado(true);		
		storage.habilitarCuenta(usuarioId);

		if ( tokenDFA != null ) {
			confirmarDFA.run(tokenDFA, usuarioId);
		}
		return usuario;
	}

	@Override
	public String crearToken(String usuario ) {
		UsuarioBO usuarioBO = storage.getUsuario(usuario);
		if (usuario == null) 
			return null;
			
		String token = tokenActivacionUsuario.crearParaUsuario(usuarioBO);
		storage.desHabilitarCuenta( usuarioBO.getId() );
		return token;
	}
	
}
