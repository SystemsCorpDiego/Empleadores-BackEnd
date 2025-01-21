package ar.ospim.empleadores.nuevo.app.servicios.usuario.interno;

import java.util.Locale;
import java.util.Optional;

import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.RolService;
import ar.ospim.empleadores.auth.usuario.dominio.usuario.servicio.UsuarioStorageEnumException;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.mail.MailHelper;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.RolBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInternoBO;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.ActualizarUsuario;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.UsuarioMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioPersonaStorage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ActualizarUsuarioInternoImpl implements ActualizarUsuarioInterno {

	private final MessageSource messageSource;
	private final UsuarioPersonaStorage usuarioPersonaStorage;
	private final RolService rolService; 
	private final ActualizarUsuario actualizarUsuario; 
	private final UsuarioMapper usuarioMapper;
	
	@Override
	public UsuarioInternoBO run(UsuarioInternoBO usuarioInterno) {
		//validar rol
		if ( usuarioInterno.getRol() == null || usuarioInterno.getRol().getId() == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Rol") );						
		}
		
		//validar mail
		if ( usuarioInterno.getPersona() != null && StringHelper.isNullOrEmptyString(usuarioInterno.getPersona().getEmail()) ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Mail") );						
		}
		if ( usuarioInterno.getPersona() != null &&  ! MailHelper.isValid(usuarioInterno.getPersona().getEmail()) ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.MAIL_FORMATO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.MAIL_FORMATO.name(), String.format(errorMsg, "Mail") );						
		}

		
		//valido Rol
		RolBO rol = null;
		rol = rolService.consultar( usuarioInterno.getRol().getId() );
		if ( rol == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.CODIGO_INVALIDO_NOMBRE.getMsgKey(), null, new Locale("es"));
			throw  new BusinessException(
        			CommonEnumException.CODIGO_INVALIDO_NOMBRE.name(),
        			String.format(errorMsg, "Rol")
        	);
		}

		//valida que no repita mail
		Optional<UsuarioInternoBO> find = usuarioPersonaStorage.findByEmail(usuarioInterno.getPersona().getEmail());
		if ( find.isPresent()) {			
			if ( !find.get().getId().equals(usuarioInterno.getId() ) ) {
				String errorMsg = messageSource.getMessage(UsuarioStorageEnumException.MAIL_DUPLICADO.getMsgKey(), null, new Locale("es"));
				throw  new BusinessException(
	        			CommonEnumException.CODIGO_INVALIDO_NOMBRE.name(),
	        			errorMsg
	        	);
			}
		}
		
		//actualizo usuario 
		//usuarioStorage.actualizar(usuarioInterno);
		
		//public UsuarioBO run(UsuarioBO usuario);
		UsuarioBO usuarioBO = usuarioMapper.map(usuarioInterno);
		usuarioBO = actualizarUsuario.run(usuarioBO);
		
		//actualizo persona
		Optional<UsuarioInternoBO> usuarioInternoBO = usuarioPersonaStorage.findByUsuarioId(usuarioInterno.getId());
		if ( usuarioInternoBO.isPresent() ) {
			usuarioInterno.getPersona().setId(usuarioInternoBO.get().getPersona().getId());
		}
		usuarioInterno = usuarioPersonaStorage.save(usuarioInterno);
		
		return usuarioInterno;
	}

}
