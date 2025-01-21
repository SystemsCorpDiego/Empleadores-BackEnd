package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Afiliado;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Afiliado.AfiliadoId;

public interface AfiliadoRepository extends JpaRepository<Afiliado, AfiliadoId> {
	
	public List<Afiliado> findByCuil(String cuil);
	
	public Optional<Afiliado> findById(AfiliadoId id);
	
}
