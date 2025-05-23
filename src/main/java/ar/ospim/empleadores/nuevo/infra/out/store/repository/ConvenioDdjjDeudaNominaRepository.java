package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjjDeudaNomina;

@Repository
public interface ConvenioDdjjDeudaNominaRepository extends JpaRepository< ConvenioDdjjDeudaNomina, Integer>{

}
