package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.UsuarioEmpresaInfoBO;

public interface EmpresaUsuarioStorage {
	
	public Optional<UsuarioEmpresaInfoBO> getUsuarioEmpresaInfo(Integer userId); 
	
	String crearTokenClaveReset(Integer usuarioId);
	public void deshabilitarUsuario(Integer userId);
	public void habilitarUsuario(Integer userId);
	public void registrarUsuario(String nombre, String email, String clave);
	public void saveUsuarioEmpresa(Integer userId, Integer personId);
	public void resetDFA(Integer userId);
	
}
