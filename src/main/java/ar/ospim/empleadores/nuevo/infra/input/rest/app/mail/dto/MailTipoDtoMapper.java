package ar.ospim.empleadores.nuevo.infra.input.rest.app.mail.dto;

import java.util.List;

import org.mapstruct.Mapper;

import ar.ospim.empleadores.nuevo.dominio.MailTipoBO;

@Mapper
public interface MailTipoDtoMapper {
	MailTipoBO map(MailTipoDto dto);
	MailTipoDto map(MailTipoBO bo);

	List<MailTipoDto> map(List<MailTipoBO> bos);
}
