package ar.ospim.empleadores.nuevo.infra.input.rest.app.ajuste;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.AjusteBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ajuste.dto.AjusteDto;

@Mapper
public interface AjusteDtoMapper {

	@Mapping(target = "empresa.cuit", source = "cuit")
	@Mapping(target = "periodo", source = "periodo_original")
	@Mapping(target = "aporte.codigo", source = "aporte")
	AjusteBO map(AjusteDto dto);
	
	@InheritInverseConfiguration
	@Mapping(target = "aporte", source = "aporte.codigo")
	@Mapping(target = "razonSocial", source = "empresa.razonSocial")	
	AjusteDto map(AjusteBO bo);
 
	List<AjusteDto> map(List<AjusteBO> lst);

	@Mapping(target = "empresa.cuit", source = "dto.cuit")
	@Mapping(target = "periodo", source = "dto.periodo_original")
	@Mapping(target = "aporte.codigo", source = "dto.aporte")
	@Mapping(target = "id", source = "id")
	AjusteBO map(AjusteDto dto, Integer id);
}
