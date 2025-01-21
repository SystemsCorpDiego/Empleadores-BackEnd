package ar.ospim.empleadores.nuevo.infra.input.rest.app.aporte;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.AporteBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.aporte.dto.AporteDto;

@Mapper 
public interface AporteDtoMapper {

	AporteBO map(AporteDto feriado);
	AporteDto map(AporteBO feriado);

	List<AporteDto> map(List<AporteBO> lst);

	@Mapping(target = "codigo", source = "codigo")
	AporteBO map(AporteDto dto, String codigo);

}
