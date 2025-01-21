package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.VCamaraCategoria;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.VCamaraCategoriaPK;

@Repository
public interface CamaraCategoriaRepository  extends JpaRepository<VCamaraCategoria, VCamaraCategoriaPK> {

	//public Optional<VCamaraCategoria> findByPkCamaraAndCategoria(String camara, String categoria);
	
}
