package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInternoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.UsuarioPersona;

@Repository
public interface UsuarioPersonaRepository extends JpaRepository<UsuarioPersona, Integer>{
	
	@Query("SELECT NEW ar.ospim.empleadores.nuevo.app.dominio.UsuarioInternoBO( U.id, U.descripcion, U.habilitado, U.ultimoLogin, U.previoLogin, UP.nombre, UP.apellido, UP.email, UP.notificaciones  ) " + 
	"FROM UsuarioPersona UP, Usuario U " +
			" WHERE UP.usuarioId = U.id" )
	List<UsuarioInternoBO> findAllUsuarioInterno();

	Optional<UsuarioPersona> findByApellidoAndNombre(String apellido, String nombre);
	
	Optional<UsuarioPersona> findByEmail(String email);
	
	Optional<UsuarioPersona> findByUsuarioId(Integer id);
	
}
