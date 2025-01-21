package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaRestringidaBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto.EmpresaRestringidaDto;

@Mapper 
public interface EmpresaRestringidaDtoMapper {
	 
	List<EmpresaRestringidaDto> map(List<EmpresaRestringidaBO> listado);
	 
	EmpresaRestringidaDto map(EmpresaRestringidaBO reg);
	EmpresaRestringidaBO map(EmpresaRestringidaDto reg);
	
	@Mapping(target = "id", source = "id")
	EmpresaRestringidaBO map(Integer id, EmpresaRestringidaDto reg);
}
