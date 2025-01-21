package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ar.ospim.empleadores.nuevo.app.dominio.DomicilioTipoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto.CodigoDescripDto;

@Mapper
public interface DomicilioTipoDtoMapper {
	CodigoDescripDto map(DomicilioTipoBO reg);
	List<CodigoDescripDto> map(List<DomicilioTipoBO> lstBo);
}
