package ar.ospim.empleadores.nuevo.infra.input.rest.app.feriado;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.FeriadoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.feriado.dto.FeriadoDto;

@Mapper 
public interface FeriadoDtoMapper {

	FeriadoBO map(FeriadoDto feriado);
	FeriadoDto map(FeriadoBO feriado);
 
	List<FeriadoDto> map(List<FeriadoBO> feriados);

	@Mapping(target = "id", source = "id")
	FeriadoBO map(Integer id, FeriadoDto feriado);

}
