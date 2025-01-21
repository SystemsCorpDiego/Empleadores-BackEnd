package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto.EmpresaModiDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt.dto.EmpresaDto;

@Mapper 
public interface EmpresaModiDtoMapper {

	
	@Mapping(target = "id", source = "id")
	@Mapping(target = "actividadMolinera", source = "dto.actividad_molinera")
	EmpresaBO map(Integer id, EmpresaModiDto dto);
	
	@Mapping(target = "actividad_molinera", source = "actividadMolinera")
	EmpresaModiDto map(EmpresaBO bo);
	
	@Mapping(target = "actividad_molinera", source = "actividadMolinera")
	EmpresaDto mapCons(EmpresaBO bo);
	List<EmpresaDto> mapCons(List<EmpresaBO> cons);
	
}
