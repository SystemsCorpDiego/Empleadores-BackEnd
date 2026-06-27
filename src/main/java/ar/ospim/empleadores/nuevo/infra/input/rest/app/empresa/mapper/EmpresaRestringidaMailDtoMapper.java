package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaRestringidaMailBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto.EmpresaRestringidaMailDto;

@Mapper
public interface EmpresaRestringidaMailDtoMapper {

	List<EmpresaRestringidaMailDto> map(List<EmpresaRestringidaMailBO> listado);

	EmpresaRestringidaMailDto map(EmpresaRestringidaMailBO reg);
	EmpresaRestringidaMailBO map(EmpresaRestringidaMailDto reg);

	@Mapping(target = "id", source = "id")
	EmpresaRestringidaMailBO map(Integer id, EmpresaRestringidaMailDto reg);
}
