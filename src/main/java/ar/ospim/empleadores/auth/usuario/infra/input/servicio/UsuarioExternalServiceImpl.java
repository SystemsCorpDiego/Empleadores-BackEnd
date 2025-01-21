package ar.ospim.empleadores.auth.usuario.infra.input.servicio;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.ActualizarClaveUsuario;
import ar.ospim.empleadores.auth.usuario.app.ActualizarLoginDate;
import ar.ospim.empleadores.auth.usuario.app.CrearTokenClaveReset;
import ar.ospim.empleadores.auth.usuario.app.DeshabilitarUsuario;
import ar.ospim.empleadores.auth.usuario.app.GetUsuarioIdByToken;
import ar.ospim.empleadores.auth.usuario.app.HabilitarUsuario;
import ar.ospim.empleadores.auth.usuario.app.ObtenerUsuarioConDFAHabilitado;
import ar.ospim.empleadores.auth.usuario.app.ResetTwoFA;
import ar.ospim.empleadores.auth.usuario.infra.input.servicio.dto.UsuarioInfoDto;
import ar.ospim.empleadores.nuevo.app.dominio.RolBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.CrearUsuario;
import ar.ospim.empleadores.nuevo.infra.out.store.UsuarioStorage;

@Service
public class UsuarioExternalServiceImpl implements UsuarioExternalService {

    private final CrearUsuario registrarUsuario;

    private final ActualizarClaveUsuario actualizarClaveUsuario;
    
	private final UsuarioStorage usuarioStorage;
	
	private final HabilitarUsuario habilitarUsuario;
	
    private final ActualizarLoginDate actualizarLoginDate;

	private final DeshabilitarUsuario deshabilitarUsuario;

	private final CrearTokenClaveReset crearTokenClaveReset;

    private final GetUsuarioIdByToken getUsuarioIdByToken;

	private final ResetTwoFA resetTwoFA;

    private final ObtenerUsuarioConDFAHabilitado obtenerUsuarioConTwoFAHabilitado;

	
    public UsuarioExternalServiceImpl(
    		CrearUsuario registrarUsuario,
    		ActualizarClaveUsuario actualizarClaveUsuario,
            UsuarioStorage usuarioStorage,
            HabilitarUsuario habilitarUsuario,
            ActualizarLoginDate actualizarLoginDate,
            DeshabilitarUsuario deshabilitarUsuario,
            CrearTokenClaveReset crearTokenClaveReset,
            GetUsuarioIdByToken getUsuarioIdByToken,
            ResetTwoFA resetTwoFA,
            ObtenerUsuarioConDFAHabilitado obtenerUsuarioConTwoFAHabilitado
            ) {
				this.registrarUsuario = registrarUsuario;
				this.actualizarClaveUsuario = actualizarClaveUsuario;
				this.usuarioStorage = usuarioStorage;
				this.habilitarUsuario = habilitarUsuario;
				this.actualizarLoginDate = actualizarLoginDate;
				this.deshabilitarUsuario = deshabilitarUsuario;
				this.crearTokenClaveReset = crearTokenClaveReset;
				this.getUsuarioIdByToken = getUsuarioIdByToken;
				this.resetTwoFA = resetTwoFA;
				this.obtenerUsuarioConTwoFAHabilitado = obtenerUsuarioConTwoFAHabilitado;
			}
		    
    @Override
    public Optional<UsuarioInfoDto> getUsuario(String key) {
        try {
            UsuarioBO userBo = usuarioStorage.getUsuario(key);
            return Optional.of(mapUserDto(userBo));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UsuarioInfoDto> getUsuario(Integer id) {
        try {
            UsuarioBO userBo = usuarioStorage.getUsuario(id);
            return Optional.of(mapUserDto(userBo));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private UsuarioInfoDto mapUserDto(UsuarioBO usuarioBo) {
        return new UsuarioInfoDto(usuarioBo.getId(), usuarioBo.getDescripcion(), usuarioBo.getClave(), usuarioBo.isHabilitado(), usuarioBo.getPrevioLogin());
    }

    @Override
    public void registrarUsuario(String username, String email, String password, RolBO rol) {
    	registrarUsuario.run(username, email, password, rol);
    }

    @Override
    public void habilitarUsuario(String username) {
    	habilitarUsuario.run(username);
    }

    @Override
    public void actualizarClave(String username, String password) {
    	actualizarClaveUsuario.run(new UsuarioBO(username), password);
    }

    @Override
    public void actualizarLoginDate(String username) {
    	actualizarLoginDate.execute(username);
    }

    @Override
    public void deshabilitarUsuario(String username){
    	deshabilitarUsuario.run(username);
    }

    @Override
    public String crearTokenClaveReset(Integer userId) {
        return crearTokenClaveReset.execute(userId);
    }

    @Override
    public Integer getUsuarioIdByToken(String token) {
        return getUsuarioIdByToken.execute(token);
    }

	@Override
	public void resetDFA(Integer userId) {
		resetTwoFA.run(userId);
	}

    @Override
    public Boolean obtenerUsuarioConDFAHabilitado(Integer userId) {
        return obtenerUsuarioConTwoFAHabilitado.run(userId);
    }
    
}
