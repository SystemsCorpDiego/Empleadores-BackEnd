package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.auth.usuario.app.RolAsignado;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.UsuarioRol;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.UsuarioRolPK;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, UsuarioRolPK> {

	// @formatter:off
	@Transactional(readOnly = true)
	@Query("SELECT ur FROM UsuarioRol as ur WHERE ur.userRolePK.userId = :userId")
	List<UsuarioRol> findByUserId(@Param("userId") Integer userId);

	@Transactional
	@Modifying
	@Query("DELETE UsuarioRol as ur "
			+ "WHERE ur.userRolePK.userId = :userId ")
	void deleteByUserId(@Param("userId") Integer userId);

	@Transactional(readOnly = true)
	@Query("SELECT NEW ar.ospim.empleadores.auth.usuario.app.RolAsignado( " +
			"	ur.userRolePK.roleId, r.descripcion )"
			+ "FROM UsuarioRol ur, Rol r "
			+ "WHERE ur.userRolePK.userId = :userId "
			+ "AND ur.userRolePK.roleId = r.id "
			)
	List<RolAsignado> getRoleAssignments(@Param("userId") Integer userId);

	@Transactional(readOnly = true)
	@Query("SELECT ur.userRolePK.userId "+
			"FROM UsuarioRol ur " +
			"WHERE ur.userRolePK.roleId IN :rolesId ")
	List<Integer> findAllByRoles(@Param("rolesId") List<Short> rolesId);
	// @formatter:on
}