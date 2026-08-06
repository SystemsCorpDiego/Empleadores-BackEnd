package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.MailTipoConfiguracion;

@Repository
public interface MailTipoConfiguracionRepository extends JpaRepository<MailTipoConfiguracion, Integer> {


	@Query("select c from MailTipoConfiguracion c where c.mailId = :mailId and c.id = "
			+ "(select max(c2.id) from MailTipoConfiguracion c2 where c2.mailId = c.mailId )")
	public Optional<MailTipoConfiguracion> findVigente(@Param("mailId") Integer mailId);

}
