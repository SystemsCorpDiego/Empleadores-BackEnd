package ar.ospim.empleadores.nuevo.infra.input.rest.auth.rol;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.RolBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto.IdDescripDto;

@Mapper 
public interface RolDtoMapper {

	RolBO map(IdDescripDto reg); 
	IdDescripDto map(RolBO reg);

	List<IdDescripDto> map(List<RolBO> lista);

	@Mapping(target = "id", source = "id")
	RolBO map(Integer id, IdDescripDto reg);


}
