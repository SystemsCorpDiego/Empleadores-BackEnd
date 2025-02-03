package ar.ospim.empleadores.auth.jwt.infra.output.usuario;
 

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.jwt.dominio.usuario.UsuarioInfoBo;
import ar.ospim.empleadores.auth.jwt.dominio.usuario.UsuarioInfoStorage;
import ar.ospim.empleadores.auth.usuario.infra.input.servicio.UsuarioExternalService;
import ar.ospim.empleadores.auth.usuario.infra.input.servicio.dto.UsuarioInfoDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioInfoStorageImpl  implements UsuarioInfoStorage {

    private final UsuarioExternalService usuarioExternalService;

    public UsuarioInfoStorageImpl(UsuarioExternalService usuarioExternalService) {
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
        log.debug("Update login date {}",username);
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
