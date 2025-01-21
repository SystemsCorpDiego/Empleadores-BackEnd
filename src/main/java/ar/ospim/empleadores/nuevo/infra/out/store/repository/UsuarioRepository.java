package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Usuario;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.UsuarioPorMailI;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Integer> {

	
	@Query(value="SELECT u.id, u.descripcion, u.dfa_habilitado dfaHabilitado FROM usuario u, usuario_persona p WHERE u.id = p.usuario_id and lower(p.email) = ?1 ",
			nativeQuery=true)
	List<UsuarioPorMailI> findByMail(String mail);
	
	@Query("SELECT u FROM Usuario u WHERE u.descripcion = :descripcion")
	Optional<Usuario> findByUsuario(@Param("descripcion") String usuario);

	@Query("SELECT u.id FROM Usuario u WHERE u.descripcion = :descripcion")
	Optional<Integer> getUsuarioId(@Param("descripcion") String usuario);

	@Transactional
	@Modifying
	@Query("UPDATE Usuario AS u SET u.habilitado = :status WHERE u.id = :id")
	void changeEstadoCuenta(@Param("id") Integer id, @Param("status") Boolean status);

	@Transactional
	@Modifying
	@Query("UPDATE Usuario AS u SET u.dfaSecreto = :secret WHERE u.id = :id")
	void setDFASecreto(@Param("id") Integer id, @Param("secret") String secret);

	@Query("SELECT u.dfaSecreto FROM Usuario u WHERE u.id = :id")
	Optional<String> getDfaSecreto(@Param("id") Integer id);

	@Transactional
	@Modifying
	@Query("UPDATE Usuario AS u SET u.dfaSecreto = NULL, u.dfaHabilitado = false WHERE u.id = :id")
	void resetDFA(@Param("id") Integer id);

	@Transactional
	@Modifying
	@Query("UPDATE Usuario AS u SET u.dfaHabilitado = true WHERE u.id = :id")
	void habilitarDFA(@Param("id") Integer id);

	@Transactional
	@Modifying
	@Query("UPDATE Usuario AS u SET u.dfaHabilitado = false, u.dfaSecreto = null WHERE u.id = :id")
	void deshabilitarDFA(@Param("id") Integer id);

	@Transactional
	@Modifying
	@Query("UPDATE Usuario AS u SET u.habilitado = false WHERE u.id = :id")
	void deshabilitar(@Param("id") Integer id);
	
	
	@Query("SELECT u.dfaHabilitado FROM Usuario AS u WHERE u.id = :id")
	Boolean usuarioConDFAHabilitado(@Param("id") Integer id);
	
}
