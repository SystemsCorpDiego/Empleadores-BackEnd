package ar.ospim.empleadores.nuevo.infra.input.rest.app.comun;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.ProvinciaBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto.ProvinciaDto;

@Mapper
public interface ProvinciaDtoMapper {
	
	@Mapping(target = "descripcion", source = "detalle")
	ProvinciaDto map(ProvinciaBO registro);

	List<ProvinciaDto> map(List<ProvinciaBO> lstRegistro);

}
