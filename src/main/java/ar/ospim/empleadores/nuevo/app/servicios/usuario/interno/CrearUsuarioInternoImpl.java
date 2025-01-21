package ar.ospim.empleadores.nuevo.app.servicios.usuario.interno;

import java.util.Locale;
import java.util.Optional;

import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.auth.usuario.dominio.usuario.servicio.UsuarioStorageEnumException;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.mail.MailHelper;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInternoBO;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.CrearUsuario;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioPersonaStorage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrearUsuarioInternoImpl implements CrearUsuarioInterno {

	private Object object;
	private final MessageSource messageSource;
	private final UsuarioPersonaStorage usuarioPersonaStorage;
	private final CrearUsuario crearUsuario;
	
	@Override
	@Transactional
	public UsuarioInternoBO run(UsuarioInternoBO usuarioInterno) {
		String errorMsg = null;
		//validar rol
		if ( usuarioInterno.getRol() == null || usuarioInterno.getRol().getId() == null ) {
			errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Rol") );						
		}
		
		//validar mail
		if ( usuarioInterno.getPersona() != null && StringHelper.isNullOrEmptyString(usuarioInterno.getPersona().getEmail()) ) {
			errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Mail") );						
		}
		if ( ! MailHelper.isValid(usuarioInterno.getPersona().getEmail()) ) {
			errorMsg = messageSource.getMessage(CommonEnumException.MAIL_FORMATO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.MAIL_FORMATO.name(), String.format(errorMsg, "Mail") );						
			
		}
		//Validar que no repita nombre apéllido
		Optional<UsuarioInternoBO> find = usuarioPersonaStorage.findByApellidoAndNombre(usuarioInterno.getPersona().getApellido(), usuarioInterno.getPersona().getNombre());
		if ( find.isPresent()) {			
			errorMsg = messageSource.getMessage(UsuarioStorageEnumException.NOMBRE_DUPLICADO.getMsgKey(), null, new Locale("es"));
			throw  new BusinessException(
        			CommonEnumException.CODIGO_INVALIDO_NOMBRE.name(),
        			errorMsg
        	);
		}
		
		//valida que no repita mail
		find = usuarioPersonaStorage.findByEmail(usuarioInterno.getPersona().getEmail());
		if ( find.isPresent()) {			
			errorMsg = messageSource.getMessage(UsuarioStorageEnumException.MAIL_DUPLICADO.getMsgKey(), null, new Locale("es"));
			throw  new BusinessException(
        			CommonEnumException.CODIGO_INVALIDO_NOMBRE.name(),
        			errorMsg
        	);
		}
		
		UsuarioBO usuarioBO = crearUsuario.run(usuarioInterno.getDescripcion(), usuarioInterno.getPersona().getEmail(), usuarioInterno.getClave(), usuarioInterno.getRol());
		usuarioInterno.setId( usuarioBO.getId() );
		
		usuarioInterno = usuarioPersonaStorage.save(usuarioInterno);
		
		//if (usuarioBO.getRol() != null && usuarioBO.getRol().getId() != null) {
			//usuarioRolAsignacionService.saveUsuarioRol(usuarioBO.getId(), usuarioBO.getRol().getId() );
		//}
		
		return usuarioInterno;
	}

}
