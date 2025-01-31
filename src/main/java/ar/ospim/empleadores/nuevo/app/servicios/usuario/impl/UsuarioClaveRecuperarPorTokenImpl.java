package ar.ospim.empleadores.nuevo.app.servicios.usuario.impl;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.dfa.app.ConfirmarDFA;
import ar.ospim.empleadores.auth.dfa.app.DeshabilitarDFA;
import ar.ospim.empleadores.auth.dfa.app.GenerarDFA;
import ar.ospim.empleadores.auth.dfa.dominio.SetDFABo;
import ar.ospim.empleadores.auth.usuario.app.TokenGestionUsuario;
import ar.ospim.empleadores.auth.usuario.app.UpdateClaveUsuario;
import ar.ospim.empleadores.auth.usuario.dominio.usuario.servicio.UsuarioStorageEnumException;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.servicios.mail.MailService;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.UsuarioClaveRecuperarPorToken;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario.dto.UsuarioClaveRecuperarPorTokenDto;
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
	
	@Override
	public String runGenToken(String mail) {
		UsuarioBO usuarioBO = usuarioStorage.getUsuarioPorMail(mail);
		
		if (usuarioBO == null) {
			//Error: no existe ese mail.-			
			String errorMsg = messageSource.getMessage(UsuarioStorageEnumException.MAIL_SIN_USUARIO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(UsuarioStorageEnumException.MAIL_SIN_USUARIO.name(), errorMsg);
		}
		
		String tokenMail = "";
		if ( usuarioBO.isDfaHabilitado() ) {
			//TODO: generar nuevo dfaToken y enviarlo en el mail
			dfaDeshabilitarService.run(usuarioBO.getId());
			
			SetDFABo dfaDto = dfaService.run(usuarioBO.getId());
			dfaHabilitarService.run( dfaDto.getSharedSecret(), usuarioBO.getId() );
			
			String tokenDFA = dfaDto.getSharedSecret();
			//usuarioStorage.setDFAToken(usuarioBO.getId(), tokenDFA);
			
			tokenMail = tokenGestionUsuario.crearParaMail(usuarioBO, mail, tokenDFA);
			mailService.runMailRecuperoClave(mail, usuarioBO.getDescripcion(), tokenMail, dfaDto);
		} else {
			tokenMail = tokenGestionUsuario.crearParaMail(usuarioBO, mail);
			mailService.runMailRecuperoClave(mail, usuarioBO.getDescripcion(), tokenMail);
		}
		
		usuarioStorage.desHabilitarCuenta(usuarioBO.getId());

		return tokenMail;
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

}
