package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ar.ospim.empleadores.nuevo.app.dominio.DeudaNominaMailConfigBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNominaMailConfig;

@Mapper
public interface DeudaNominaMailConfigMapper {

	DeudaNominaMailConfig map(DeudaNominaMailConfigBO reg);
	DeudaNominaMailConfigBO map(DeudaNominaMailConfig reg);

	@Mapping(target = "id", ignore = true)
	void map(DeudaNominaMailConfigBO reg, @MappingTarget DeudaNominaMailConfig regNew);

}
