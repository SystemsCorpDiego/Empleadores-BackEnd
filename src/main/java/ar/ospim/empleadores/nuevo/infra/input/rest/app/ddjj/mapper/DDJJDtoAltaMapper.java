package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.AfiliadoBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJEmpleadoDto;

@Mapper 
public interface DDJJDtoAltaMapper {

	@Mapping(target = "empresa.id", source = "empresaId")
	@Mapping(target = "id", ignore = true)
	DDJJBO mapAlta(Integer empresaId, DDJJAltaDto reg); 
	
	@Mapping(target = "inte", constant = "0")
	AfiliadoBO dDJJEmpleadoDtoToAfiliadoBO(DDJJEmpleadoDto dDJJEmpleadoDto);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "aportes", ignore = true)
	@Mapping(target = "afiliado.cuil", source = "cuil")
	@Mapping(target = "afiliado.inte", source = "inte")
	@Mapping(target = "afiliado.apellido", source = "apellido")
	@Mapping(target = "afiliado.nombre", source = "nombre")
	@Mapping(target = "ingreso", source = "fechaIngreso")
	@Mapping(target = "empresaDomicilio.id", source = "empresaDomicilioId") 
	DDJJEmpleadoBO mapEmpleadoAlta(DDJJEmpleadoDto emple);
}
