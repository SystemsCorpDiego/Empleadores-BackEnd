package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.VEmpresaUsuario;

@Repository
public interface EmpresaUsuarioRepository extends JpaRepository<VEmpresaUsuario, Integer>  {

	public Optional<VEmpresaUsuario> findByEmpresaId(Integer empresaId);
}
