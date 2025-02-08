package ar.ospim.empleadores.nuevo.app.servicios.usuario.impl;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.dfa.app.ConfirmarDFA;
import ar.ospim.empleadores.auth.dfa.app.DeshabilitarDFA;
import ar.ospim.empleadores.auth.dfa.app.GenerarDFA;
import ar.ospim.empleadores.auth.dfa.dominio.SetDFABo;
import ar.ospim.empleadores.auth.jwt.app.login.LoginEnumException;
import ar.ospim.empleadores.auth.usuario.app.TokenGestionUsuario;
import ar.ospim.empleadores.auth.usuario.app.UpdateClaveUsuario;
import ar.ospim.empleadores.auth.usuario.dominio.usuario.servicio.UsuarioStorageEnumException;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.servicios.mail.MailService;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.UsuarioClaveRecuperarPorToken;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.UsuarioMailGet;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario.dto.UsuarioClaveRecuperarPorTokenDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.UsuarioRecuperoClaveTokenDto;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioClaveRecuperarPorTokenImpl implements UsuarioClaveRecuperarPorToken {

	private final MessageSource messageSource;
	private TokenGestionUsuario tokenGestionUsuario;
	private UpdateClaveUsuario updateClaveUsuario;
	private UsuarioStorage usuarioStorage;
	private GenerarDFA dfaService;
	private DeshabilitarDFA dfaDeshabilitarService;
	private ConfirmarDFA dfaHabilitarService;
	private MailService mailService;
	private UsuarioMailGet usuarioMailGet;
	
	
	@Override
	public UsuarioRecuperoClaveTokenDto runGenTokenByUsuarioDescrip(String descripcion) {
		UsuarioRecuperoClaveTokenDto dto = null;
		UsuarioBO usuario = usuarioStorage.getUsuario(descripcion);
		if (usuario == null) {
			String errorMsg = messageSource.getMessage(UsuarioStorageEnumException.USUARIO_NOT_FOUND.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(UsuarioStorageEnumException.USUARIO_NOT_FOUND.name(), errorMsg);
		}
		if ( !usuario.isHabilitado() ) {
			String errorMsg = messageSource.getMessage(LoginEnumException.USUARIO_DESHABILITADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(LoginEnumException.USUARIO_DESHABILITADO.name(), errorMsg);
		}
		
		String mail = usuarioMailGet.run(usuario);
		if ( mail == null ) {
			String errorMsg = messageSource.getMessage(UsuarioStorageEnumException.USUARIO_SIN_MAIL.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(UsuarioStorageEnumException.USUARIO_SIN_MAIL.name(), errorMsg);
		}
		String tokenMail = runGenToken(usuario, mail);
		
		if ( tokenMail != null ) {
			dto = new UsuarioRecuperoClaveTokenDto();
			dto.setMail(mail);
			dto.setToken(tokenMail);
		}
		return dto;
	}
	
	@Override
	public UsuarioRecuperoClaveTokenDto runGenTokenByMail(String mail) {
		UsuarioRecuperoClaveTokenDto dto = null;
		UsuarioBO usuario = usuarioStorage.getUsuarioPorMail(mail);
		
		if (usuario == null) {
			String errorMsg = messageSource.getMessage(UsuarioStorageEnumException.MAIL_SIN_USUARIO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(UsuarioStorageEnumException.MAIL_SIN_USUARIO.name(), errorMsg);
		}
		
		String tokenMail = runGenToken(usuario, mail);
		
		if ( tokenMail != null ) {
			dto = new UsuarioRecuperoClaveTokenDto();
			dto.setMail(mail);
			dto.setToken(tokenMail);
		}
		return dto;
	}

	@Override
	public UsuarioClaveRecuperarPorTokenDto runConsul(String token) {
		UsuarioClaveRecuperarPorTokenDto dto = new UsuarioClaveRecuperarPorTokenDto();
		dto.setUsuario( tokenGestionUsuario.getUsuario(token) );
		dto.setEmail( tokenGestionUsuario.getMail(token) );
		
		return dto;
	}

	@Override
	public void runActualizarClave(String token, String clave) {
		Integer usuarioId = tokenGestionUsuario.getId(token);		
		UsuarioBO usuario = usuarioStorage.getUsuario(usuarioId);
		updateClaveUsuario.run(usuario, clave);	
		usuarioStorage.habilitarCuenta(usuarioId);
	}

	private String runGenToken(UsuarioBO usuario, String mail) {
		String tokenMail = "";
		if ( usuario.isDfaHabilitado() ) {
			//TODO: generar nuevo dfaToken y enviarlo en el mail
			dfaDeshabilitarService.run(usuario.getId());
			
			SetDFABo dfaDto = dfaService.run(usuario.getId());
			dfaHabilitarService.run( dfaDto.getSharedSecret(), usuario.getId() );
			
			String tokenDFA = dfaDto.getSharedSecret();
			//usuarioStorage.setDFAToken(usuarioBO.getId(), tokenDFA);
			
			tokenMail = tokenGestionUsuario.crearParaMail(usuario, mail, tokenDFA);
			mailService.runMailRecuperoClave(mail, usuario.getDescripcion(), tokenMail, dfaDto);
		} else {
			tokenMail = tokenGestionUsuario.crearParaMail(usuario, mail);
			mailService.runMailRecuperoClave(mail, usuario.getDescripcion(), tokenMail);
		}
		
		usuarioStorage.desHabilitarCuenta(usuario.getId());

		return tokenMail;		
	}
	
}
