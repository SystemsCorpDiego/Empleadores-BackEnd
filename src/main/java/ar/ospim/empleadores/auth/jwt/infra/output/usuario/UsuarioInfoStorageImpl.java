package ar.ospim.empleadores.auth.jwt.infra.output.usuario;
 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.jwt.dominio.usuario.UsuarioInfoBo;
import ar.ospim.empleadores.auth.jwt.dominio.usuario.UsuarioInfoStorage;
import ar.ospim.empleadores.auth.usuario.infra.input.servicio.UsuarioExternalService;
import ar.ospim.empleadores.auth.usuario.infra.input.servicio.dto.UsuarioInfoDto;

@Service
public class UsuarioInfoStorageImpl  implements UsuarioInfoStorage {

    private final Logger logger;

    private final UsuarioExternalService usuarioExternalService;

    public UsuarioInfoStorageImpl(UsuarioExternalService usuarioExternalService) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.usuarioExternalService = usuarioExternalService;
    }
   
    @Override
    public UsuarioInfoBo getUsuario(String usuario) {
        return usuarioExternalService.getUsuario(usuario)
                .map(this::toUsuarioInfoBo)
                .orElse(null);
    }

	@Override
	public UsuarioInfoBo getUsuario(Integer userId) {
		return usuarioExternalService.getUsuario(userId)
				.map(this::toUsuarioInfoBo)
				.orElse(null);
	}
	
	@Override
    public void actualizarLoginDate(String username) {
        logger.debug("Update login date {}",username);
        usuarioExternalService.actualizarLoginDate(username);
    }

    @Override
    public Boolean obtenerUsuarioConDFAHabilitado(Integer userId) {
        return usuarioExternalService.obtenerUsuarioConDFAHabilitado(userId);
    }

    private UsuarioInfoBo toUsuarioInfoBo(UsuarioInfoDto userInfoDto) {
        return new UsuarioInfoBo(userInfoDto.getId(),
                userInfoDto.getUsuario(),
                userInfoDto.isHabilitado(),
                userInfoDto.getClave());
    }    
}
