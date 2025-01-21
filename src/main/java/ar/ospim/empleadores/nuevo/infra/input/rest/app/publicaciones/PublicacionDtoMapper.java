package ar.ospim.empleadores.nuevo.infra.input.rest.app.publicaciones;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.PublicacionBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.publicaciones.dto.PublicacionDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.publicaciones.dto.PublicacionVigenteDto;

@Mapper 
public interface PublicacionDtoMapper {

	PublicacionBO map(PublicacionDto reg);
	PublicacionDto map(PublicacionBO reg);

	List<PublicacionDto> map(List<PublicacionBO> lista);

	@Mapping(target = "id", source = "id")
	PublicacionBO map(Integer id, PublicacionDto reg);

	List<PublicacionVigenteDto> mapVig(List<PublicacionBO> lista);
	PublicacionVigenteDto mapVig(PublicacionBO reg);

}
