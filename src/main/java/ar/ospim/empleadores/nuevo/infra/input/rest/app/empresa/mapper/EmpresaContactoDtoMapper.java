package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.ContactoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto.EmpresaContactoAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto.EmpresaContactoDto;

@Mapper
public interface EmpresaContactoDtoMapper {
	
	ContactoBO map(EmpresaContactoDto dto);
	
	EmpresaContactoDto map(ContactoBO bo);

	
	List<EmpresaContactoDto> map(List<ContactoBO> lstBo);

	ContactoBO map(EmpresaContactoAltaDto dto);
	
	@Mapping(target = "id", source = "id")
	ContactoBO map(Integer id, EmpresaContactoDto dto);

}
