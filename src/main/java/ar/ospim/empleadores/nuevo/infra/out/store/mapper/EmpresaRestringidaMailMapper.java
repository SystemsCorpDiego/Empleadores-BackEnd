package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaRestringidaMailBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.EmpresaRestringidaMail;

@Mapper
public interface EmpresaRestringidaMailMapper {

	EmpresaRestringidaMail map(EmpresaRestringidaMailBO reg);
	EmpresaRestringidaMailBO map(EmpresaRestringidaMail reg);

	@Mapping(target = "id", ignore = true)
	void map(EmpresaRestringidaMailBO reg, @MappingTarget EmpresaRestringidaMail regNew);

	List<EmpresaRestringidaMailBO> map(List<EmpresaRestringidaMail> listado);
}
