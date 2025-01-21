package ar.ospim.empleadores.nuevo.app.servicios.usuario.impl;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.RolService;
import ar.ospim.empleadores.auth.usuario.dominio.clave.ClaveEncriptador;
import ar.ospim.empleadores.auth.usuario.dominio.usuario.modelo.OAuthUsuarioBo;
import ar.ospim.empleadores.auth.usuario.dominio.usuario.servicio.OAuthUsuarioManagementStorage;
import ar.ospim.empleadores.auth.usuario.dominio.usuarioclave.ValidatorClave;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.RolBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.CrearUsuario;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.RegistrarUsuarioEnumException;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage; 

@Service
public class CrearUsuarioImpl implements CrearUsuario {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Value("${app.seguridad.perfilEmpleador}")
    private Short rolEmpleadorId;
    
    private final MessageSource messageSource;
    
    private final UsuarioStorage usuarioStorage;
    private final RolService rolService; 
    
    private final OAuthUsuarioManagementStorage oAuthUsuarioManagementStorage;

    private final Pattern usuarioPattern;
    
    private final ValidatorClave validatorClave;
    
    private final ClaveEncriptador claveEncriptador;

    private final String defaultClave;

    @Value("${ws.oauth.enabled:false}")
    private boolean oAuthServiceEnabled;
    
 
    public CrearUsuarioImpl(UsuarioStorage usuarioStorage,
    					OAuthUsuarioManagementStorage oAuthUsuarioManagementStorage,
    					RolService rolService,
                            @Value("${authentication.username.pattern:.+}") String pattern,
                            ValidatorClave validatorClave,
                            ClaveEncriptador claveEncriptador,
                            @Value("${authentication.username.password.default:PRUEBA}") String defaultClave,
                            MessageSource messageSource) {
    	this. validatorClave = validatorClave;
        this.claveEncriptador = claveEncriptador;
        this.defaultClave = defaultClave;
        this.usuarioStorage = usuarioStorage;
        this.rolService = rolService;
        this.oAuthUsuarioManagementStorage = oAuthUsuarioManagementStorage;
        this.usuarioPattern = Pattern.compile(pattern);
        this.messageSource = messageSource;
    }
    
	@Override
	public UsuarioBO run(String usuario, String email, String clave, RolBO rol) {
        logger.debug("Register new user -> {}", usuario);
        validacionesUsuario(usuario);
        validacionesClave(clave);
        
        registrarEnOAuthService(usuario, email, clave);
        
        return register(usuario, clave, rol);
	}

	@Override
	public UsuarioBO run(UsuarioBO usuario) {

		//valido rol
		if ( usuario.getRol() != null && usuario.getRol().getId() != null) {
			RolBO rol = rolService.consultar( usuario.getRol().getId() );
			if ( rol == null ) {
				String errorMsg = messageSource.getMessage(CommonEnumException.CODIGO_INVALIDO_NOMBRE.getMsgKey(), null, new Locale("es"));
				throw  new BusinessException(
	        			CommonEnumException.CODIGO_INVALIDO_NOMBRE.name(),
	        			String.format(errorMsg, "Rol")
	        	);
			}
		}
		
		return run(usuario.getDescripcion(), null, usuario.getClave(), usuario.getRol() );
		 
	}

    private void registrarEnOAuthService(String usuario, String email, String clave) {
        logger.debug("Input parameters -> username {}, email {}", usuario, email);
        if (oAuthServiceEnabled) {
        	OAuthUsuarioBo oAuthUsuarioBo = new OAuthUsuarioBo(usuario, clave, null, null, email);
        	oAuthUsuarioManagementStorage.crearUsuario(oAuthUsuarioBo);
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
    
    private void validacionesUsuario(String usuario) {
        Objects.requireNonNull(usuario, () -> {
        	String errorMsg = messageSource.getMessage(RegistrarUsuarioEnumException.USUARIO_OBLIGADO.getMsgKey(), null, new Locale("es"));
        	throw new BusinessException(RegistrarUsuarioEnumException.USUARIO_OBLIGADO.name(), errorMsg);
        });
        if (!usuarioPattern.matcher(usuario).matches()) {
        	String errorMsg = messageSource.getMessage(RegistrarUsuarioEnumException.USUARIO_PATRON_INVALIDO.getMsgKey(), null, new Locale("es"));        	
        	throw new BusinessException(RegistrarUsuarioEnumException.USUARIO_PATRON_INVALIDO.name(), 
        			String.format(errorMsg, usuario, usuarioPattern.pattern()) );
        }
        
        if ( usuarioStorage.existe(usuario) ) {
        	String errorMsg = messageSource.getMessage(RegistrarUsuarioEnumException.USUARIO_DUPLICADO.getMsgKey(), null, new Locale("es"));        	
        	throw new BusinessException(RegistrarUsuarioEnumException.USUARIO_PATRON_INVALIDO.name(), 
        			String.format(errorMsg, usuario) );
        }
        
    }
    
    private UsuarioBO register(String usuario, String clave, RolBO rol) {
        logger.debug("Input parameter -> usuario {}", usuario);
        var salt = "salt";
        var hashAlgorithm = "hashAlgorithm";
        UsuarioBO userBo = (clave != null) ?
                new UsuarioBO(usuario, claveEncriptador.codificar(clave,salt,hashAlgorithm), salt, hashAlgorithm) :
                new UsuarioBO(usuario);
        userBo.setRol(rol);
        
        userBo.setHabilitado(true);
        if ( rol.getId().equals( rolEmpleadorId ) ) {
        	userBo.setHabilitado(false);
        }
        
        return usuarioStorage.guardar(userBo);
    }

	
}
