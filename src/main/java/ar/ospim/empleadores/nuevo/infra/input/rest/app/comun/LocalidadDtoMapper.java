package ar.ospim.empleadores.nuevo.infra.input.rest.app.comun;

import java.util.List;

import org.mapstruct.Mapper;

import ar.ospim.empleadores.nuevo.app.dominio.LocalidadBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto.LocalidadDto;

@Mapper
public interface LocalidadDtoMapper {
	
	LocalidadDto map(LocalidadBO registro);

	List<LocalidadDto> map(List<LocalidadBO> lstRegistro);

}
