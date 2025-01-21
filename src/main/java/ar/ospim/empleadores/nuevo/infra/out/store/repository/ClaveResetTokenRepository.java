package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ClaveResetToken;

@Repository  
public interface ClaveResetTokenRepository extends JpaRepository<ClaveResetToken, Long> {

	@Query("SELECT PRT FROM ClaveResetToken PRT " +
			" WHERE PRT.token = :token" +
			" AND PRT.habilitado = true" +
			" AND PRT.bajaFecha > CURRENT_TIMESTAMP")
	Optional<ClaveResetToken> findByToken(@Param("token") String token);

	@Transactional
	@Modifying
	@Query("UPDATE ClaveResetToken AS PRT " +
			" SET PRT.habilitado = false " +
			" WHERE PRT.usuarioId = :usuarioId")
	void deshabilitarTokens(@Param("usuarioId") Integer usuarioId);

}
