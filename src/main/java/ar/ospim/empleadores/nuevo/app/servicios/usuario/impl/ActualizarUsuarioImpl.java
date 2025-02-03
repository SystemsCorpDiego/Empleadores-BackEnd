package ar.ospim.empleadores.nuevo.app.servicios.usuario.impl;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.dominio.clave.ClaveEncriptador;
import ar.ospim.empleadores.auth.usuario.dominio.usuarioclave.ValidatorClave;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.RolBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInternoBO;
import ar.ospim.empleadores.nuevo.app.servicios.mail.MailService;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.ActualizarUsuario;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.RegistrarUsuarioEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.interno.ConsultarUsuarioInterno;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActualizarUsuarioImpl implements ActualizarUsuario {
    private final MessageSource messageSource;

    private final Pattern usuarioPattern;
    private final ClaveEncriptador claveEncriptador;
    private final UsuarioStorage usuarioStorage;
    private final ValidatorClave validatorClave;
    private final ConsultarUsuarioInterno consultarUsuarioInterno; 
    private final MailService mailService; 
    
    public ActualizarUsuarioImpl(UsuarioStorage usuarioStorage,
    					MessageSource messageSource,
		                @Value("${authentication.username.pattern:.+}") String pattern,
		                ClaveEncriptador claveEncriptador,
		                ValidatorClave validatorClave,
		                ConsultarUsuarioInterno consultarUsuarioInterno,
		                MailService mailService) {		
		this.usuarioStorage = usuarioStorage;
		this.messageSource = messageSource;
		this.claveEncriptador = claveEncriptador; 
		this.usuarioPattern = Pattern.compile(pattern);
		this.validatorClave = validatorClave;
		this.consultarUsuarioInterno = consultarUsuarioInterno;
		this.mailService = mailService;
	}
    
    
	@Override
	public UsuarioBO run(UsuarioBO usuario) {
        log.debug("ActualizarUsuario new user -> {}", usuario);
        validaciones(usuario);
        validacionesClave(usuario.getClave());
        //registrarEnOAuthService(usuario, email, clave);
        
        return actualizar(usuario.getId(), usuario.getDescripcion(), usuario.getClave(), usuario.getRol(), usuario.isHabilitado() );
	}

	private UsuarioBO actualizar(Integer id, String usuario, String clave, RolBO rol, Boolean habilitado) {
        log.debug("Input parameter -> usuario {}", usuario);
        var salt = "salt";
        var hashAlgorithm = "hashAlgorithm";
        UsuarioBO usuarioNew = (clave != null) ?
                new UsuarioBO(id, usuario, claveEncriptador.codificar(clave,salt,hashAlgorithm), salt, hashAlgorithm) :
                new UsuarioBO(id, usuario);
        usuarioNew.setRol(rol);
        usuarioNew.setHabilitado(habilitado);
        
        UsuarioBO usuarioOld = usuarioStorage.getUsuario(id);
        
        boolean esClaveNueva = false;
        if ( clave != null) {
        	esClaveNueva = esClaveNueva(usuarioOld, clave);
        }
        
        UsuarioBO regNew = usuarioStorage.actualizar(usuarioNew);

        if ( esClaveNueva) {
        	UsuarioInternoBO usuaIntSesion = consultarUsuarioInterno.getFromSession(); 
        	UsuarioInternoBO usuaIntModif = consultarUsuarioInterno.run(id);
        	
        	//20250131: Mando siempre SIN copia
        	mailService.runCambioDeClave(usuarioOld.getDescripcion(),  clave, usuaIntModif.getPersona().getEmail(),  null);
        	/*
        	if ( usuaIntSesion.getPersona() != null && usuaIntSesion.getPersona().getEmail() != null) {        		
        		mailService.runCambioDeClave(usuarioOld.getDescripcion(),  clave, usuaIntModif.getPersona().getEmail(), usuaIntSesion.getPersona().getEmail());
        	}  else {
        		mailService.runCambioDeClave(usuarioOld.getDescripcion(),  clave, usuaIntModif.getPersona().getEmail(),  null);
        	}
        	*/
        }
        return regNew;
	}
	
	private boolean esClaveNueva(UsuarioBO usuarioOld, String claveNueva) {
		 return !claveEncriptador.iguales(claveNueva, usuarioOld.getClave());
	}
	 

	private void validaciones(UsuarioBO usuario) {
		
		if (usuario.getId() == null ) {
        	String errorMsg = messageSource.getMessage(CommonEnumException.CODIGO_NO_INFORMADO.getMsgKey(), null, new Locale("es"));
        	throw new BusinessException(CommonEnumException.CODIGO_NO_INFORMADO.name(), errorMsg);			
		}		
        Objects.requireNonNull(usuario.getDescripcion(), () -> {
        	String errorMsg = messageSource.getMessage(RegistrarUsuarioEnumException.USUARIO_OBLIGADO.getMsgKey(), null, new Locale("es"));
        	throw new BusinessException(RegistrarUsuarioEnumException.USUARIO_OBLIGADO.name(), errorMsg);
        });
        if (!usuarioPattern.matcher(usuario.getDescripcion()).matches()) {
        	String errorMsg = messageSource.getMessage(RegistrarUsuarioEnumException.USUARIO_PATRON_INVALIDO.getMsgKey(), null, new Locale("es"));        	
        	throw new BusinessException(RegistrarUsuarioEnumException.USUARIO_PATRON_INVALIDO.name(), 
        			String.format(errorMsg, usuario, usuarioPattern.pattern()) );
        }
        
        if ( usuarioStorage.existe(usuario.getDescripcion()) ) {
        	UsuarioBO usuarioBO = usuarioStorage.getUsuario(usuario.getDescripcion());
        	if ( !usuarioBO.getId().equals( usuario.getId() )  ) {
	        	String errorMsg = messageSource.getMessage(RegistrarUsuarioEnumException.USUARIO_DUPLICADO.getMsgKey(), null, new Locale("es"));        	
	        	throw new BusinessException(RegistrarUsuarioEnumException.USUARIO_PATRON_INVALIDO.name(), 
	        			String.format(errorMsg, usuario) );
        	}
        }        
    }
	
	private void validacionesClave(String pwd) {
    	if ( pwd != null ) {
	    	if ( !validatorClave.esValida(pwd) ) {
	        	String errorMsg = messageSource.getMessage(RegistrarUsuarioEnumException.PASSWORD_PATRON_INVALIDO.getMsgKey(), null, new Locale("es"));        	
	        	throw new BusinessException(RegistrarUsuarioEnumException.PASSWORD_PATRON_INVALIDO.name(), errorMsg );
	    	}
    	}
    }         
}
