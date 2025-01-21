package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ar.ospim.empleadores.nuevo.app.dominio.AfipInteresBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AfipInteres;

@Mapper
public interface AfipInteresMapper {
	AfipInteres map(AfipInteresBO reg);
	AfipInteresBO map(AfipInteres reg);
	
	List<AfipInteresBO> map(List<AfipInteres> reg);
	
	@Mapping( target="id", ignore = true)  
	void map(@MappingTarget AfipInteres newReg, AfipInteresBO reg);

}
