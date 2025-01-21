package ar.ospim.empleadores.nuevo.app.servicios.usuario.interno;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInternoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioPersonaStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioSesionStorage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConsultarUsuarioInternoImpl implements ConsultarUsuarioInterno {

	private final UsuarioPersonaStorage storage;
	private final MessageSource messageSource;
	private final UsuarioSesionStorage sesionStorage;
	
	@Override
	public List<UsuarioInternoBO> run() {
		return storage.findAllUsuarioInterno(); 
	}

	@Override
	public UsuarioInternoBO run(Integer usuarioId) {
		// TODO Auto-generated method stub
		Optional<UsuarioInternoBO> cons =  storage.findByUsuarioId(usuarioId);
		if ( cons.isEmpty()) {
			String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), String.format(errorMsg, usuarioId) );					
		}
		return cons.get();
	}

	@Override
	public UsuarioInternoBO getFromSession() {
		// TODO Auto-generated method stub
		Integer id = sesionStorage.getUsuarioId();
		Optional<UsuarioInternoBO> usuario = storage.findByUsuarioId(id);
		if ( usuario.isEmpty()) {
			String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), String.format(errorMsg, id) );					
		}
		return usuario.get();				
	}

	
}
