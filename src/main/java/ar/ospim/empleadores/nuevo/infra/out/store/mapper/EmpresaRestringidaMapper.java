package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaRestringidaBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.EmpresaRestringida;

@Mapper 
public interface EmpresaRestringidaMapper {
 
	EmpresaRestringida map(EmpresaRestringidaBO reg);
	EmpresaRestringidaBO map(EmpresaRestringida reg);
	
	@Mapping( target="id", ignore = true)  
	void map(EmpresaRestringidaBO reg, @MappingTarget EmpresaRestringida regNew);

	List<EmpresaRestringidaBO> map(List<EmpresaRestringida> listado);

}

