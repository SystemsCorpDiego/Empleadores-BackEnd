package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjj;

@Repository
public interface ConvenioDdjjRepository    extends JpaRepository< ConvenioDdjj, Integer> {

}
