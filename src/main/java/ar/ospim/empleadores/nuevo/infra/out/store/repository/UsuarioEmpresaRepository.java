package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.UsuarioEmpresa;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.UsuarioEmpresaPK;

@Repository
public interface UsuarioEmpresaRepository extends JpaRepository<UsuarioEmpresa, UsuarioEmpresaPK> {

	@Query("SELECT (case when count(ue.pk.usuarioId)> 0 then true else false end) "
			+ "FROM UsuarioEmpresa ue "
			+ "WHERE ue.pk.empresaId = :empresaId")
	boolean existsByEmpresaId(@Param("empresaId") Integer empresaId);

	@Query("SELECT ue.pk.usuarioId FROM UsuarioEmpresa ue WHERE ue.pk.empresaId = :empresaId")
	Optional<Integer> getUsuarioIdByEmpresaId(@Param("empresaId") Integer empresaId);

	@Query("SELECT ue.pk.empresaId FROM UsuarioEmpresa ue WHERE ue.pk.usuarioId = :usuarioId")
	Optional<Integer> getEmpresaIdByUsuarioId(@Param("usuarioId") Integer userId);

	@Query("SELECT ue FROM UsuarioEmpresa ue WHERE ue.pk.usuarioId = :usuarioId")
    Optional<UsuarioEmpresa> getByUsuarioId(@Param("usuarioId") Integer usuarioId);
	
	@Query(value=" select u.id "
			+ " from   usuario u, empresa e, empresa_contacto c "
			+ " where  u.descripcion = e.cuit "
			+ " and    e.id = c.empresa_id "
			+ " and    c.tipo = 'MAIL' "
			+ " and    upper(c.valor) = upper( ?1 ) ",
			nativeQuery=true)
	List<Integer> findByMail(String mail);
	
}
