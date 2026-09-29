package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.dominio.MailTipoConfiguracionBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.mail.dto.MailTipoConfiguracionDto;

@Mapper
public interface MailTipoConfiguracionDtoMapper {

	MailTipoConfiguracionDto map(MailTipoConfiguracionBO reg);
	MailTipoConfiguracionBO map(MailTipoConfiguracionDto reg);

	List<MailTipoConfiguracionDto> map(List<MailTipoConfiguracionBO> lst);
	
	@Mapping(target = "id", source = "id")
	MailTipoConfiguracionBO map(Integer id, MailTipoConfiguracionDto reg);

}
