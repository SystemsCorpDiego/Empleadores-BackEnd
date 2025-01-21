package ar.ospim.empleadores.nuevo.infra.input.rest.app.afiliado;

import java.util.List;

import org.mapstruct.Mapper;

import ar.ospim.empleadores.nuevo.app.dominio.AfiliadoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.afiliado.dto.AfiliadoDto;

@Mapper
public interface AfiliadoDtoMapper {

	AfiliadoDto map(AfiliadoBO reg);
	List<AfiliadoDto> map(List<AfiliadoBO> reg);
	
}
