package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ar.ospim.empleadores.nuevo.app.dominio.DeudaNominaMailConfigBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNominaMailConfig;

@Mapper
public interface DeudaNominaMailConfigMapper {

	DeudaNominaMailConfig map(DeudaNominaMailConfigBO reg);
	DeudaNominaMailConfigBO map(DeudaNominaMailConfig reg);
	
	List<DeudaNominaMailConfigBO> map(List<DeudaNominaMailConfig> lst);
	

	@Mapping(target = "id", ignore = true)
	void map(DeudaNominaMailConfigBO reg, @MappingTarget DeudaNominaMailConfig regNew);

}
