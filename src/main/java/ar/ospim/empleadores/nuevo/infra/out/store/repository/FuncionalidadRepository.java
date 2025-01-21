package ar.ospim.empleadores.nuevo.infra.out.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Funcionalidad;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.FuncionalidadConsultaI;

@Repository
public interface FuncionalidadRepository  extends JpaRepository<Funcionalidad, Integer>{
	

	@Query(value =" select f.id, f.descripcion"
			+ " from funcionalidad f "
			+ " where not exists ( select 1 from rol_funcionalidad rf "
			+ " where rf.funcionalidad = f.descripcion "
			+ " and  rf.rol = ?1) ", 
			nativeQuery=true
			)
	public List<FuncionalidadConsultaI> getFaltanteRol(String rol);
	
	
	@Query(value =" select f.id, f.descripcion"
			+ " from funcionalidad f "
			+ " where exists ( select 1 from rol_funcionalidad rf "
			+ " where rf.funcionalidad = f.descripcion "
			+ " and     rf.activo = true "
			+ " and     rf.rol = ?1) ", 
			nativeQuery=true
			)
	public List<FuncionalidadConsultaI> getByRol(String rol);
	
	@Query(value =" select count(1) "
			+ " from usuario_rol ur, rol r, rol_funcionalidad rf "
			+ " where ur.usuario_id = ?1 "
			+ " and   ur.baja = false "
			+ " and   r.baja = false "
			+ " and    rf.activo = true "
			+ " and    r.id = ur.rol_id "
			+ " and   rf.funcionalidad = 'ID_EMPRESA_TEST' "
			+ " and    rf.rol = r.descripcion", 
			nativeQuery=true
			)
	public Integer getFuncTestCuitHabi(Integer usuarioId);
	
}
