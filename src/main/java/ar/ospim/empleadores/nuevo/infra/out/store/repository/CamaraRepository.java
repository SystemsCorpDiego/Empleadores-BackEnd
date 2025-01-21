package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.VCamara;

@Repository
public interface CamaraRepository extends JpaRepository<VCamara, String>  {

	Optional<VCamara> findByCodigo(String codigo);
	
}
