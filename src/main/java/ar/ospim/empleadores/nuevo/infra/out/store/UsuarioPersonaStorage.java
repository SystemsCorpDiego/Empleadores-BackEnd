package ar.ospim.empleadores.nuevo.infra.out.store;

import java.util.List;
import java.util.Optional;

import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInternoBO;

public interface UsuarioPersonaStorage {
	
	public UsuarioInternoBO save(UsuarioInternoBO regBO);
	
	public List<UsuarioInternoBO> findAllUsuarioInterno();
	
	public Optional<UsuarioInternoBO> findByApellidoAndNombre(String Apellido, String nombre);
	public Optional<UsuarioInternoBO> findByEmail(String email);
	public Optional<UsuarioInternoBO> findByUsuarioId(Integer id);
	
}
