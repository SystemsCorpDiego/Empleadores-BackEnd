package ar.ospim.empleadores.nuevo.app.servicios.ddjj;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoBO;

@Mapper 
public interface DDJJBoMapper {
	
	public void map(@MappingTarget DDJJBO ddjjBo, DDJJBO ddjjBoNew);
	
	public void map(@MappingTarget DDJJEmpleadoBO ddjjEmpleadoBO, DDJJEmpleadoBO ddjjEmpleadoBONew);
	
	@Mapping(target = "aportes", ignore = true)
	@Mapping(target = "id", ignore = true) 
	public void mapSinAportes(@MappingTarget DDJJEmpleadoBO ddjjEmpleadoBO, DDJJEmpleadoBO ddjjEmpleadoBONew);
	 
}
