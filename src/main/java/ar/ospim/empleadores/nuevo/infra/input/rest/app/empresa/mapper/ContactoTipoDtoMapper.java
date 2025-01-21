package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ar.ospim.empleadores.nuevo.app.dominio.ContactoTipoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto.CodigoDescripDto;

@Mapper
public interface ContactoTipoDtoMapper {

	CodigoDescripDto map(ContactoTipoBO reg);
	List<CodigoDescripDto> map(List<ContactoTipoBO> lstBo);

}
