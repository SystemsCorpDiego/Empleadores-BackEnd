package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.time.LocalDate;
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

	
	@Query(value = "select fmail_notificacion_fechaEnvioDesde_consul as fecha from fmail_notificacion_fechaEnvioDesde_consul( :mailId, now()\\:\\:date ) ", nativeQuery = true)
	public Optional<LocalDate> getFechaEnvioDesde(@Param("mailId") Integer mailId);
	
	@Query(value = "select fmail_notificacion_fechaEnvioHasta_consul as fecha from fmail_notificacion_fechaEnvioHasta_consul( now()\\:\\:date ) ", nativeQuery = true)
	public Optional<LocalDate> getFechaEnvioHasta();
	
}
