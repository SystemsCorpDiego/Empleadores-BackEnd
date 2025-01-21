package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.UserKey;

@Repository
public interface UserKeyRepository extends JpaRepository<UserKey, Integer> {

	@Query("SELECT uk " +
			"FROM UserKey uk " +
			"WHERE uk.pk.key = :key")
	Optional<UserKey> getUserKeyByKey(@Param("key") String key);

}
