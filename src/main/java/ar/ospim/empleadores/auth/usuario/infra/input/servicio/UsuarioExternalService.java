package ar.ospim.empleadores.auth.usuario.infra.input.servicio;

import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.RolBO;
import ar.ospim.empleadores.auth.usuario.infra.input.servicio.dto.UsuarioInfoDto;

public interface UsuarioExternalService {

    Optional<UsuarioInfoDto> getUsuario(String usuario);
    Optional<UsuarioInfoDto> getUsuario(Integer userId);
    Integer getUsuarioIdByToken(String token);
    
    void registrarUsuario(String usuario, String email, String clave, RolBO rol);
    void habilitarUsuario(String usuario);
    void actualizarClave(String usuario, String clave);
    void actualizarLoginDate(String usuario);
    void deshabilitarUsuario(String usuario);
	void resetDFA(Integer userId);

    String crearTokenClaveReset(Integer userId);
    Boolean obtenerUsuarioConDFAHabilitado(Integer userId);
}
