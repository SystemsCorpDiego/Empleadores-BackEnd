package ar.ospim.empleadores.nuevo.app.servicios.usuario.interno;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.RolService;
import ar.ospim.empleadores.comun.seguridad.UsuarioInfo;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInternoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioPersonaStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.UsuarioRolRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ActualizarPersonaUsuarioLogueadoImpl implements ActualizarPersonaUsuarioLogueado {

	private final ActualizarUsuarioInterno actualizarUsuarioInterno;
	private final UsuarioPersonaStorage usuarioPersonaStorage;
	private final UsuarioStorage usuarioStorage;
	private final UsuarioRolRepository usuarioRolRepository;
	private final RolService rolService;
	  
	
	@Override
	public UsuarioInternoBO run(UsuarioInternoBO usuarioInterno) {
		//solo actualizo usuario logueado
		
		UsuarioBO usuario = usuarioStorage.getUsuario(UsuarioInfo.getCurrentAuditor() );
		//List<UsuarioRol> lstRoles = usuarioRolRepository.findByUserId(UsuarioInfo.getCurrentAuditor() );
		//RolBO rol = rolService.consultar(lstRoles.get(0).getId().getRoleId());
		
		
		Optional<UsuarioInternoBO> cons = usuarioPersonaStorage.findByUsuarioId( UsuarioInfo.getCurrentAuditor() );
		if ( cons.isPresent() ) {
			//solo cambio apellido, nombre y mail => usuario_persona
			UsuarioInternoBO reg = cons.get();
			reg.getPersona().setEmail(usuarioInterno.getPersona().getEmail());
			reg.getPersona().setApellido(usuarioInterno.getPersona().getApellido());
			reg.getPersona().setNombre(usuarioInterno.getPersona().getNombre());
			
			reg.setDescripcion( usuario.getDescripcion());
			reg.setRol(usuario.getRol());
			reg.setHabilitado(usuario.isHabilitado());
			reg.setUltimoLogin(usuario.getUltimoLogin());
			
			usuarioInterno = actualizarUsuarioInterno.run(reg);
		}
		
		return usuarioInterno;
	}

}
