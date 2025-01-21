package ar.ospim.empleadores.nuevo.app.servicios.usuario.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.dfa.app.ConfirmarDFA;
import ar.ospim.empleadores.auth.dfa.app.DeshabilitarDFA;
import ar.ospim.empleadores.auth.dfa.app.GenerarDFA;
import ar.ospim.empleadores.auth.dfa.dominio.SetDFABo;
import ar.ospim.empleadores.auth.usuario.app.TokenGestionUsuario;
import ar.ospim.empleadores.auth.usuario.app.UpdateClaveUsuario;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.servicios.mail.MailService;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.UsuarioClaveRecuperarPorToken;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario.dto.UsuarioClaveRecuperarPorTokenDto;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;

@Service
public class UsuarioClaveRecuperarPorTokenImpl implements UsuarioClaveRecuperarPorToken {

	@Autowired
	private TokenGestionUsuario tokenGestionUsuario;
	
	@Autowired
	private UpdateClaveUsuario updateClaveUsuario;
	
	@Autowired
	private UsuarioStorage usuarioStorage;

	@Autowired
	private GenerarDFA dfaService;
	
	@Autowired
	private DeshabilitarDFA dfaDeshabilitarService;
	
	@Autowired
	private ConfirmarDFA dfaHabilitarService;
	
	@Autowired
	private MailService mailService;
	
	@Override
	public String runGenToken(String mail) {
		UsuarioBO usuarioBO = usuarioStorage.getUsuarioPorMail(mail);
		
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
