package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNominaMailConfig;

@Repository
public interface DeudaNominaMailConfigRepository extends JpaRepository<DeudaNominaMailConfig, Long> {

	public Optional<DeudaNominaMailConfig> findByFechaProceso(LocalDate fechaProceso);

	@Query("select c from DeudaNominaMailConfig c where c.fechaProceso = "
			+ "(select max(c2.fechaProceso) from DeudaNominaMailConfig c2 where c2.fechaProceso <= current_date)")
	public Optional<DeudaNominaMailConfig> findVigente();

 	@Query(value="SELECT * FROM fGestion_deuda_notificacion_mail_getFechaProceso(); ", nativeQuery=true)
	LocalDate getFechaProcesoVigente();

}
