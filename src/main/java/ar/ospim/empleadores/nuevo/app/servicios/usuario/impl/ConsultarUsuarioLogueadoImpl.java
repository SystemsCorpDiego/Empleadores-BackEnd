package ar.ospim.empleadores.nuevo.app.servicios.usuario.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.jwt.app.login.LoginEnumException;
import ar.ospim.empleadores.auth.usuario.app.RolAsignado;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioEmpresaInfoBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInfoBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInternoBO;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.ConsultarUsuarioLogueado;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.UsuarioInfoMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaUsuarioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioPersonaStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioSesionStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.ERol;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.FuncionalidadRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.FuncionalidadConsultaI;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConsultarUsuarioLogueadoImpl implements ConsultarUsuarioLogueado {
	
	private MessageSource messageSource;
	
	private final UsuarioSesionStorage store;
	private final UsuarioStorage usuarioStorage;
	private final UsuarioPersonaStorage usuarioPersonaStorage;
	private final EmpresaUsuarioStorage empresaUsuarioStorage;
	private final UsuarioInfoMapper mapper;
	private final FuncionalidadRepository funcionalidadRepository;
	
	@Override
	public UsuarioInfoBO run() {
		Integer usuarioId = store.getUsuarioId();
		
		if ( usuarioId.equals(-1) ) {
			String errorMsg = messageSource.getMessage(LoginEnumException.USUARIO_ANONIMO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(LoginEnumException.USUARIO_ANONIMO.name(), errorMsg  );
		}
		
		UsuarioBO usuario = usuarioStorage.getUsuario( usuarioId ); 
		List<RolAsignado> rolesAsignados = store.getRolesAsignados();
		
		
		RolAsignado rol = rolesAsignados.stream().filter(rolAsig -> rolAsig.getId().equals(ERol.EMPLEADOR.getId()) )
		  .findAny()
		  .orElse(null);
		
		Optional<UsuarioEmpresaInfoBO> usuarioEmpresa = Optional.empty();
		if ( rol != null) {
			usuarioEmpresa = empresaUsuarioStorage.getUsuarioEmpresaInfo( usuario.getId() );
		}
		
		Optional<UsuarioInternoBO>  usuarioInternoBO = usuarioPersonaStorage.findByUsuarioId(usuarioId);
		
		UsuarioInfoBO dto = mapper.map(usuario, rolesAsignados, usuarioEmpresa.orElse(null));
		dto.setUsuarioInternoBO( usuarioInternoBO.orElse(null) );

		if ( rolesAsignados != null && rolesAsignados.size()>0) {
			List<FuncionalidadConsultaI> funcionalidades = funcionalidadRepository.getByRol(rolesAsignados.get(0).getDescripcion());
			dto.setFuncionalidadesAsignadas(mapper.map(funcionalidades));
		}
		return dto;
	}
	
	
}
