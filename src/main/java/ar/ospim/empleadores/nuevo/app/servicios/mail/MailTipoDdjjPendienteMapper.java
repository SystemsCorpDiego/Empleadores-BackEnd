package ar.ospim.empleadores.nuevo.app.servicios.mail;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.dominio.MailTipoDdjjPendienteBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.IMailTipoDdjjPendienteDto;

@Mapper
public interface MailTipoDdjjPendienteMapper {

    @Mapping(target = "razonSocial", source = "razon_social")    
    @Mapping(target = "email", source = "mail")
	MailTipoDdjjPendienteBO run(IMailTipoDdjjPendienteDto dto);
	List<MailTipoDdjjPendienteBO> run(List<IMailTipoDdjjPendienteDto> dto);

}
