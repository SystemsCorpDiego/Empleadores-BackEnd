package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;

public interface UsuarioStorage {
	public UsuarioBO guardar(UsuarioBO user) ;
	public UsuarioBO actualizar(UsuarioBO userBo);
	public UsuarioBO getUsuario(Integer userId);
	public UsuarioBO getUsuario(String usuarioNombre);
	public UsuarioBO getUsuarioPorMail(String mail);
	public Optional<Integer> getUsuarioIdByEmpresaId(Integer empresaId);
	public Optional<Integer> getEmpresaIdByUsuarioId(Integer usuarioId);
	public void resetDFA(Integer userId);
	public void setDFAToken(Integer userId, String tokenDFA);
	public void habilitarCuenta(Integer usuarioId);
	public void desHabilitarCuenta(Integer usuarioId);
	public Boolean usuarioConDFAHabilitado(Integer userId);
	public boolean existe(String usuarioNombre);
	
}
