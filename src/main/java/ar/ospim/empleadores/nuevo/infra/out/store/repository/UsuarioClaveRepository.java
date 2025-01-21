package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.UsuarioClave;

@Repository
public interface UsuarioClaveRepository  extends JpaRepository<UsuarioClave, Integer> {

	@Query("SELECT up.clave FROM UsuarioClave up WHERE up.id = :id ") //ORDER BY up.creationable.createdOn DESC
	Optional<String> getPasswordById(@Param("id") Integer id);

	@Transactional
	@Modifying
	@Query("UPDATE UsuarioClave AS up SET up.clave = :password WHERE up.id = :userId") //, up.updateable.updatedOn = :today 
	void updatePassword(@Param("userId") Integer userId, @Param("password") String password ); 

	@Query("SELECT COUNT(up.id) FROM UsuarioClave up WHERE up.id = :id ") //AND up.updateable.updatedOn > :tokenDate
	int passwordUpdateAfter(@Param("id") Integer id); 

}
