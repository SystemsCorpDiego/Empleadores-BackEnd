package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ar.ospim.empleadores.nuevo.app.dominio.RolBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Rol;

@Mapper 
public interface RolMapper {
	 
	Rol map(RolBO regBO);
	RolBO map(Rol reg);

	@Mapping( target="id", ignore = true)  
	void map(@MappingTarget Rol rolNew, RolBO rol);

	List<RolBO> map(List<Rol> lstReg);

}
