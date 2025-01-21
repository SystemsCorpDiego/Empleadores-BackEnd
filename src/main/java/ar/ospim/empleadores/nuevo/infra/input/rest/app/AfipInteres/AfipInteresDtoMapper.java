package ar.ospim.empleadores.nuevo.infra.input.rest.app.AfipInteres;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.AfipInteresBO;

@Mapper
public interface AfipInteresDtoMapper {

	@Mapping(target = "desde", source = "vigenciaDesde")
	@Mapping(target = "hasta", source = "vigenciaHasta")
	AfipInteresBO map(AfipInteresDto dto);
	
	@InheritInverseConfiguration
	AfipInteresDto map(AfipInteresBO bo);

	List<AfipInteresDto> map(List<AfipInteresBO> lst);

	@Mapping(target = "id", source = "id")
	@Mapping(target = "desde", source = "dto.vigenciaDesde")
	@Mapping(target = "hasta", source = "dto.vigenciaHasta")
	AfipInteresBO map(AfipInteresDto dto, Integer id);

	
}
