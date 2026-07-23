package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.DeudaNominaMailConfigBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.DeudaNominaMailConfigDto;

@Mapper
public interface DeudaNominaMailConfigDtoMapper {

	DeudaNominaMailConfigDto map(DeudaNominaMailConfigBO reg);
	DeudaNominaMailConfigBO map(DeudaNominaMailConfigDto reg);

	List<DeudaNominaMailConfigDto> map(List<DeudaNominaMailConfigBO> lst);
	
	@Mapping(target = "id", source = "id")
	DeudaNominaMailConfigBO map(Long id, DeudaNominaMailConfigDto reg);

}
