package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.dominio.MailTipoEmpresaRestringidaBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.mail.dto.MailTipoEmpresaRestringidaDto;

@Mapper
public interface EmpresaRestringidaMailDtoMapper {

	List<MailTipoEmpresaRestringidaDto> map(List<MailTipoEmpresaRestringidaBO> listado);

	MailTipoEmpresaRestringidaDto map(MailTipoEmpresaRestringidaBO reg);
	MailTipoEmpresaRestringidaBO map(MailTipoEmpresaRestringidaDto reg);

	@Mapping(target = "id", source = "id")
	MailTipoEmpresaRestringidaBO map(Integer id, MailTipoEmpresaRestringidaDto reg);
}
