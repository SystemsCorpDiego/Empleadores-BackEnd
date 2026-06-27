package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNominaMailConfig;

@Repository
public interface DeudaNominaMailConfigRepository extends JpaRepository<DeudaNominaMailConfig, Long> {

}
