package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioAjuste;

@Repository
public interface ConvenioAjusteRepository   extends JpaRepository< ConvenioAjuste, Integer> {

}
