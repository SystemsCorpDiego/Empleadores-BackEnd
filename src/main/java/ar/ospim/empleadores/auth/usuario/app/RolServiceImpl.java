package ar.ospim.empleadores.auth.usuario.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.strings.StringHelper;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.RolBO;
import ar.ospim.empleadores.nuevo.infra.out.store.RolStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.UsuarioRolRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

	private final MessageSource messageSource;
	private final RolStorage storage;
	private final UsuarioRolRepository usuaRolRepo; 

	@Override
	public RolBO crear(RolBO registro) {
		
		
		if (registro == null || StringHelper.isNullOrWhiteSpace( registro.getDescripcion() ) ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Descripción") );
		} else {
			Optional<RolBO> cons = storage.findByDescripcion(registro.getDescripcion());
			if ( cons.isPresent()) {				
				String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_DUPLICADO.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.REGISTRO_DUPLICADO.name(), String.format(errorMsg,  registro.getDescripcion() ) );
			}
		}
		return storage.save(registro);
	}

	@Override
	public void borrar(Short id) {
		if (id == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ID_NO_INFORMADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ID_NO_INFORMADO.name(), errorMsg);
		}
		
		List<Short> aux = new ArrayList<Short>();
		aux.add(id);
		List<Integer> cons =usuaRolRepo.findAllByRoles(aux);
		if (cons != null && cons.size() > 0) {
			String errorMsg = messageSource.getMessage(CommonEnumException.CODIGO_CON_FK.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.CODIGO_CON_FK.name(), String.format(errorMsg, "Usuarios"));			
		}
		
		storage.deleteById(id);		
	}

	@Override
	public List<RolBO> consultar() {
		return storage.findAll();
	}
	
	@Override
	public List<RolBO> usuarioInternoConsultar() {
		List<RolBO> lst = storage.findAll();
		lst = lst.stream().filter(reg -> reg.getId() >4 ).collect(Collectors.toList());		
		return lst;
	}

	@Override
	public RolBO consultar(Short id ) {
		Optional<RolBO> reg = storage.findById(id);
		if ( reg.isEmpty()) {
			//ERROR
			return null;
		}
		return reg.get();
	}
	 
	

}
