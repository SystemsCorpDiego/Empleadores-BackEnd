package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNomina;

public interface DeudaNominaRepository extends JpaRepository<DeudaNomina, Long> {

	List<DeudaNomina> getByCuitAndActaIdIsNull(String cuit);
	
}
